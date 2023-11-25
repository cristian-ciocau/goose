package com.cciocau.goose;

import com.cciocau.goose.efb.EFBService;
import com.cciocau.goose.output.*;
import com.cciocau.goose.sensor.SensorManager;
import com.cciocau.goose.sensor.adsb.AdsbTraffic;
import com.cciocau.goose.sensor.adsb.dump1090.Dump1090Receiver;
import com.cciocau.goose.sensor.gps.GpsConfig;
import com.cciocau.goose.sensor.gps.GpsData;
import com.cciocau.goose.efb.EFBType;
import com.cciocau.goose.sensor.gps.gpsd.GpsdReceiver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Goose {
    private static final Logger logger = LogManager.getLogger(Goose.class);

    private final EFBService efbService = new EFBService();
    private final ScheduledExecutorService executorService;

    private volatile GpsData gpsData = new GpsData();
    private volatile AdsbTraffic trafficData = new AdsbTraffic(new ArrayList<>());

    public Goose() {
        this(Executors.newScheduledThreadPool(5));
    }

    public Goose(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public void run() {
        SensorManager<GpsData> gpsSensorManager = new SensorManager<>();
        SensorManager<AdsbTraffic> trafficSensorManager = new SensorManager<>();

        var gpsReceiver = new GpsdReceiver(gpsSensorManager);
        var trafficReceiver = new Dump1090Receiver(trafficSensorManager);

        gpsSensorManager.subscribe(data -> gpsData = data);
        gpsSensorManager.subscribe(data -> logger.debug("GPS data available={}", data));

        trafficSensorManager.subscribe(data -> trafficData = data);
        trafficSensorManager.subscribe(data -> logger.debug("Traffic data available={}", data));

        // GPS Listener
        executorService.schedule(gpsReceiver::receive, 0, TimeUnit.SECONDS);

        // ADS-B Reader
        executorService.scheduleAtFixedRate(trafficReceiver::receive, 0, 1, TimeUnit.SECONDS);

        // EFB Sender
        executorService.scheduleAtFixedRate(this::sendToEFB, 0, 1, TimeUnit.SECONDS);
    }

    private void sendToEFB() {
        if (!checkGPSValid()) {
            gpsData = new GpsData();
        }

        try {
            final List<EFBClient> clients = new ArrayList<>();

            efbService.getFlightBags(EFBType.FOREFLIGHT)
                    .stream()
                    .map(efb -> new ForeFlightClient(efb.getAddress()))
                    .forEach(clients::add);

            clients.add(new LoggingEFBClient());

            var combinedClient = new CombinedEFBClient(clients);
            var sender = new ForeFlightSender(combinedClient);

            sender.send(gpsData, trafficData);


        } catch (Exception ex) {
            logger.error("Failed to send data to EFB", ex);
        }
    }

    private boolean checkGPSValid() {
        var config = new GpsConfig();

        return gpsData.getTime().toInstant()
                .plusSeconds(config.getFixValidDuration().toSeconds())
                .isAfter(Instant.now());
    }

    public static void main(String[] args) {
        var goose = new Goose();
        goose.run();
    }
}

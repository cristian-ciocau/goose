package com.cciocau.goose.sensor.gps.gpsd;

import com.cciocau.goose.sensor.SensorManager;
import com.cciocau.goose.sensor.gps.GpsData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class GpsdReceiver {
    private static final Logger logger = LogManager.getLogger(GpsdReceiver.class);

    private static final String GPSD_LISTEN_COMMAND = "?WATCH={\"enable\":true,\"json\":true}\r\n";

    private final GpsdConfig config;
    private final SensorManager<GpsData> sensorManager;

    public GpsdReceiver(SensorManager<GpsData> sensorManager) {
        this(new GpsdConfig(), sensorManager);
    }

    public GpsdReceiver(GpsdConfig config, SensorManager<GpsData> sensorManager) {
        this.config = config;
        this.sensorManager = sensorManager;
    }

    public void receive() {
        try (var socket = new Socket(config.getHost(), config.getPort())) {
            socket.getOutputStream().write(GPSD_LISTEN_COMMAND.getBytes());

            var input = socket.getInputStream();

            var reader = new GpsdReader(input);
            var stream = reader.read();

            stream.forEach(sensorManager::notify);

        } catch (Exception ex) {
            logger.error("Failed to receive GPS data - {}", ex.getMessage());
        }
    }
}

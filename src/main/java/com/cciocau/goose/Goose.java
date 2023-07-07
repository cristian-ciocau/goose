package com.cciocau.goose;

import com.cciocau.goose.efb.EFBService;
import com.cciocau.goose.output.*;
import com.cciocau.goose.sensor.SensorManager;
import com.cciocau.goose.sensor.gps.GpsData;
import com.cciocau.goose.efb.EFBType;
import com.cciocau.goose.sensor.gps.gpsd.GpsdReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Goose {

    public static void main(String[] args) {
        var efbService = new EFBService();
        SensorManager<GpsData> gpsSensorManager = new SensorManager<>();

        var gpsReceiver = new GpsdReceiver(gpsSensorManager);

        BlockingQueue<GpsData> queue = new ArrayBlockingQueue<>(20);

        gpsSensorManager.subscribe(data -> {
            var result = queue.offer(data);

            System.out.println("Queue GPS data accept=" + result);
        });

        var executorService = Executors.newScheduledThreadPool(10);

        // GPS Listener
        executorService.schedule(gpsReceiver::receive, 0, TimeUnit.SECONDS);

//        executorService.scheduleAtFixedRate(() -> {
//            System.out.println(repository.getAll());
//        }, 0, 1, TimeUnit.SECONDS);

        // EFB Sender
        executorService.scheduleAtFixedRate(() -> {
            try {
                final List<EFBClient> clients = new ArrayList<>();

                efbService.getFlightBags(EFBType.FOREFLIGHT)
                        .stream()
                        .map(efb -> new ForeFlightClient(efb.getAddress()))
                        .forEach(clients::add);

                clients.add(new ConsoleEFBClient());

                var combinedClient = new CombinedEFBClient(clients);
                var sender = new ForeFlightSender(combinedClient);

                var gpsData = Optional.ofNullable(getLast(queue));
                sender.send(gpsData);


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    private static <T> T getLast(BlockingQueue<T> queue) {
        var item = queue.poll();

        if (item != null) {
            var temp = queue.poll();

            if (temp == null) {
                return item;
            }

            item = temp;
        }

        return item;
    }
}

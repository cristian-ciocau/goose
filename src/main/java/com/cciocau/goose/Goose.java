package com.cciocau.goose;

import com.cciocau.goose.data.*;
import com.cciocau.goose.gps.gpsd.GpsdReader;
import com.cciocau.goose.gps.GpsData;
import com.cciocau.goose.efb.EFBRepository;
import com.cciocau.goose.efb.EFBType;
import com.cciocau.goose.efb.foreflight.ForeFlightListener;
import com.cciocau.goose.gps.GpsPosition;
import com.cciocau.goose.output.CombinedEFBClient;
import com.cciocau.goose.output.ConsoleEFBClient;
import com.cciocau.goose.output.EFBClient;
import com.cciocau.goose.output.ForeFlightClient;
import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;
import com.google.common.collect.EvictingQueue;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Goose {
//    private static final String GPS_HOST = "localhost";
    private static final String GPS_HOST = "192.168.10.1";

    public static void main(String[] args) {
        var repository = new EFBRepository();
        var listener = new ForeFlightListener(repository);

        Queue<GpsData> queue = EvictingQueue.create(2);

        var executorService = Executors.newScheduledThreadPool(10);

        // GPS Listener
        executorService.schedule(() -> {
            try (var socket = new Socket(GPS_HOST, 2947)) {
                socket.getOutputStream().write("?WATCH={\"enable\":true,\"json\":true}\r\n".getBytes());

                var input = socket.getInputStream();

                var reader = new GpsdReader(input);
                var stream = reader.read();

                stream.forEach(queue::add);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 0, TimeUnit.SECONDS);

        // ForeFlight Listener
        executorService.schedule(listener::listen, 0, TimeUnit.SECONDS);

        // EFB Sender
//        executorService.scheduleAtFixedRate(() -> {
//            System.out.println(repository.getAll());
//        }, 0, 1, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(() -> {
            try {
                final List<EFBClient> clients = new ArrayList<>();

                repository.getByType(EFBType.FOREFLIGHT)
                        .stream()
                        .map(efb -> new ForeFlightClient(efb.getAddress()))
                        .forEach(clients::add);

                clients.add(new ConsoleEFBClient());

                var combinedClient = new CombinedEFBClient(clients);

                send(combinedClient, queue);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    private static void send(EFBClient client, Queue<GpsData> queue) {
        client.sendHeartbeat(new Heartbeat());

        client.sendMessageId(new ForeFlightMessageId("Goose"));

        var queueItem = Optional.ofNullable(queue.poll());

        var position = queueItem
                .map(GpsData::getPosition)
                .map(gpsPosition -> new Position(gpsPosition.getLatitude(), gpsPosition.getLongitude(), 0, gpsPosition.getAccuracy()))
                .orElseGet(() -> new Position(0, 0, 0, 0));

        var speed = queueItem
                .flatMap(GpsData::getSpeed);

        var track = queueItem
                .flatMap(GpsData::getTrack)
                .map(Double::intValue)
                .map(value -> new Track(TrackType.TRUE, value))
                .orElse(null);

        var aircraft = new Aircraft(0x404C21, IcaoAircraftCategory.LIGHT, "GOOSE", track, speed, position);


        client.sendOwnShip(new OwnShip(aircraft));

        queueItem.map(GpsData::getPosition)
                .map(GpsPosition::getAltitudeFeet)
                .map(OwnShipGeometricAltitude::new)
                .ifPresent(client::sendGeometricAltitude);
    }
}

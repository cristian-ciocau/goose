package com.cciocau.goose.output;

import com.cciocau.goose.protocol.data.*;
import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.TrafficReport;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;
import com.cciocau.goose.sensor.adsb.AdsbTraffic;
import com.cciocau.goose.sensor.gps.GpsData;

public class ForeFlightSender {
    private final EFBClient client;

    public ForeFlightSender(EFBClient client) {
        this.client = client;
    }

    public void send(GpsData gpsData, AdsbTraffic trafficData) {
        client.sendHeartbeat(new Heartbeat());

        client.sendMessageId(new ForeFlightMessageId("Goose"));

        var position = new Position(gpsData.getPosition());

        var speed = gpsData.getSpeed();

        var track = gpsData.getTrack()
                .map(value -> new Track(TrackType.TRUE, value))
                .orElse(null);

        var aircraft = new Aircraft(0x404C21, IcaoAircraftCategory.LIGHT, "GOOSE", track, speed, position);


        client.sendOwnShip(new OwnShip(aircraft));

        client.sendGeometricAltitude(new OwnShipGeometricAltitude(gpsData.getPosition().getAltitude(), gpsData.getPosition().getVerticalError()));

        trafficData.getAircraftList().stream()
                .map(TrafficReport::new)
                .forEach(client::sendTrafficReport);
    }
}

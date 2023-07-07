package com.cciocau.goose.output;

import com.cciocau.goose.data.*;
import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;
import com.cciocau.goose.sensor.gps.GpsData;

import java.util.Optional;

public class ForeFlightSender {
    private final EFBClient client;

    public ForeFlightSender(EFBClient client) {
        this.client = client;
    }

    public void send(Optional<GpsData> gpsData) {
        client.sendHeartbeat(new Heartbeat());

        client.sendMessageId(new ForeFlightMessageId("Goose"));

        // TODO - send OwnShip when GPS is not locked
        // sending an empty position is meh
        var position = gpsData
                .map(GpsData::getPosition)
                .map(gpsPosition -> new Position(gpsPosition.getLatitude(), gpsPosition.getLongitude(), gpsPosition.getAltitude(), gpsPosition.getLatitudeError(), gpsPosition.getLongitudeError()))
                .orElse(null);

        var speed = gpsData
                .flatMap(GpsData::getSpeed);

        var track = gpsData
                .flatMap(GpsData::getTrack)
                .map(Double::intValue)
                .map(value -> new Track(TrackType.TRUE, value))
                .orElse(null);

        var aircraft = new Aircraft(0x404C21, IcaoAircraftCategory.LIGHT, "GOOSE", track, speed, position);


        client.sendOwnShip(new OwnShip(aircraft));

        gpsData.map(GpsData::getPosition)
                .map(gpsPosition -> new OwnShipGeometricAltitude(gpsPosition.getAltitude(), gpsPosition.getVerticalError()))
                .ifPresent(client::sendGeometricAltitude);
    }
}

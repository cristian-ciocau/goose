package com.cciocau.goose.output;

import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;

public class ConsoleEFBClient implements EFBClient {
    private final Gson gson;

    public ConsoleEFBClient() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Optional.class, new GsonOptionalSerDes<>()).create();
    }

    @Override
    public void sendHeartbeat(Heartbeat heartbeat) {
        log(heartbeat);
    }

    @Override
    public void sendMessageId(ForeFlightMessageId messageId) {
        log(messageId);
    }

    @Override
    public void sendOwnShip(OwnShip ownShip) {
        log(ownShip);
    }

    @Override
    public void sendGeometricAltitude(OwnShipGeometricAltitude geometricAltitude) {
        log(geometricAltitude);
    }

    private void log(Object data) {
//        var json = gson.toJson(data);

        System.out.println(data.toString());
    }
}

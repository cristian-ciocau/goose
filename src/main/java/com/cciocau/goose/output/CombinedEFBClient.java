package com.cciocau.goose.output;

import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;

import java.util.List;

public class CombinedEFBClient implements EFBClient {
    private final List<EFBClient> clients;

    public CombinedEFBClient(List<EFBClient> clients) {
        this.clients = clients;
    }

    @Override
    public void sendHeartbeat(Heartbeat heartbeat) {
        clients.forEach(client -> client.sendHeartbeat(heartbeat));
    }

    @Override
    public void sendMessageId(ForeFlightMessageId messageId) {
        clients.forEach(client -> client.sendMessageId(messageId));
    }

    @Override
    public void sendOwnShip(OwnShip ownShip) {
        clients.forEach(client -> client.sendOwnShip(ownShip));
    }

    @Override
    public void sendGeometricAltitude(OwnShipGeometricAltitude geometricAltitude) {
        clients.forEach(client -> client.sendGeometricAltitude(geometricAltitude));
    }
}

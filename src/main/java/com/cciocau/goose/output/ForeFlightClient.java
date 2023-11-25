package com.cciocau.goose.output;

import com.cciocau.goose.protocol.gdl90.*;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;

import java.net.InetSocketAddress;

public class ForeFlightClient implements EFBClient {
    private final GDL90Client gdl90Client;

    public ForeFlightClient(InetSocketAddress address) {
        gdl90Client = new GDL90Client(address);
    }

    public void sendHeartbeat(Heartbeat heartbeat) {
        gdl90Client.send(heartbeat.generate());
    }

    public void sendMessageId(ForeFlightMessageId messageId) {
        gdl90Client.send(messageId.generate());
    }

    public void sendOwnShip(OwnShip ownShip) {
        gdl90Client.send(ownShip.generate());
    }

    public void sendGeometricAltitude(OwnShipGeometricAltitude geometricAltitude) {
        gdl90Client.send(geometricAltitude.generate());
    }

    @Override
    public void sendTrafficReport(TrafficReport trafficReport) {
        gdl90Client.send(trafficReport.generate());
    }
}

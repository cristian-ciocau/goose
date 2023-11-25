package com.cciocau.goose.output;

import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.TrafficReport;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;

public interface EFBClient {
    void sendHeartbeat(Heartbeat heartbeat);

    void sendMessageId(ForeFlightMessageId messageId);

    void sendOwnShip(OwnShip ownShip);

    void sendGeometricAltitude(OwnShipGeometricAltitude geometricAltitude);

    void sendTrafficReport(TrafficReport trafficReport);
}

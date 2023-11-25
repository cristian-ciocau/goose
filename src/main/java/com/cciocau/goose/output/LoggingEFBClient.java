package com.cciocau.goose.output;

import com.cciocau.goose.protocol.gdl90.Heartbeat;
import com.cciocau.goose.protocol.gdl90.OwnShip;
import com.cciocau.goose.protocol.gdl90.OwnShipGeometricAltitude;
import com.cciocau.goose.protocol.gdl90.TrafficReport;
import com.cciocau.goose.protocol.gdl90.foreflight.ForeFlightMessageId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggingEFBClient implements EFBClient {
    private static final Logger logger = LogManager.getLogger(LoggingEFBClient.class);

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

    @Override
    public void sendTrafficReport(TrafficReport trafficReport) {
        log(trafficReport);
    }

    private void log(Object data) {
        logger.info(data.toString());
    }
}

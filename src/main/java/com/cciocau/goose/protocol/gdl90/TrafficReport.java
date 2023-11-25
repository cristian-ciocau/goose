package com.cciocau.goose.protocol.gdl90;

import com.cciocau.goose.protocol.data.Aircraft;

public class TrafficReport extends BaseTrafficReport implements Message {
    private static final byte TRAFFIC_REPORT_MESSAGE_TYPE = 20;

    private final Aircraft aircraft;

    public TrafficReport(Aircraft aircraft) {
        super(TRAFFIC_REPORT_MESSAGE_TYPE);

        this.aircraft = aircraft;
    }

    @Override
    public byte[] generate() {
        return createMessage(aircraft);
    }

    @Override
    public String toString() {
        return "TrafficReport{" +
                "aircraft=" + aircraft +
                '}';
    }
}

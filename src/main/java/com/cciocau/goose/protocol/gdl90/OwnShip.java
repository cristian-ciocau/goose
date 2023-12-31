package com.cciocau.goose.protocol.gdl90;

import com.cciocau.goose.protocol.data.Aircraft;

public class OwnShip extends BaseTrafficReport implements Message {
    private static final byte OWN_SHIP_MESSAGE_TYPE = 10;

    private final Aircraft aircraft;

    public OwnShip(Aircraft aircraft) {
        super(OWN_SHIP_MESSAGE_TYPE);

        this.aircraft = aircraft;
    }

    @Override
    public byte[] generate() {
        return createMessage(aircraft);
    }

    @Override
    public String toString() {
        return "OwnShip{" +
                "aircraft=" + aircraft +
                '}';
    }
}

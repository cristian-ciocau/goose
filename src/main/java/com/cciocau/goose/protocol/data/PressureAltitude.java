package com.cciocau.goose.protocol.data;

public class PressureAltitude {
    private final int value;

    public PressureAltitude(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PressureAltitude{" +
                "value=" + value +
                '}';
    }
}

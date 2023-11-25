package com.cciocau.goose.protocol.data;

import javax.measure.Quantity;
import javax.measure.quantity.Angle;

public class Track {
    private final TrackType type;
    private final Quantity<Angle> value;

    public Track(TrackType type, Quantity<Angle> value) {
        this.type = type;
        this.value = value;
    }

    public TrackType getType() {
        return type;
    }

    public Quantity<Angle> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Track{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}

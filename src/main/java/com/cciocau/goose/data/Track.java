package com.cciocau.goose.data;

public class Track {
    private final TrackType type;
    private final int value;

    public Track(TrackType type, int value) {
        this.type = type;
        this.value = value;
    }

    public TrackType getType() {
        return type;
    }

    public int getValue() {
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

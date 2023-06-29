package com.cciocau.goose.gps;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import java.util.Optional;

public class GpsData {
    private final GpsPosition gpsPosition;
    private final Double track;
    private final Optional<Quantity<Speed>> speed;

    public GpsData(GpsPosition gpsPosition, Double track, Optional<Quantity<Speed>> speed) {
        this.gpsPosition = gpsPosition;
        this.track = track;
        this.speed = speed;
    }

    public GpsPosition getPosition() {
        return gpsPosition;
    }

    public Optional<Double> getTrack() {
        return Optional.ofNullable(track);
    }

    public Optional<Quantity<Speed>> getSpeed() {
        return speed;
    }
}

package com.cciocau.goose.sensor.gps;

import com.cciocau.goose.sensor.SensorData;
import com.cciocau.goose.sensor.gps.gpsd.NMEAMode;

import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Speed;
import java.util.Optional;

public class GpsData implements SensorData {
    private final NMEAMode nmeaMode;
    private final GpsPosition gpsPosition;
    private final Optional<Quantity<Angle>> track;
    private final Optional<Quantity<Speed>> speed;

    public GpsData() {
        this(NMEAMode.UNKNOWN, new GpsPosition(), Optional.empty(), Optional.empty());
    }

    public GpsData(NMEAMode nmeaMode, GpsPosition gpsPosition, Optional<Quantity<Angle>> track, Optional<Quantity<Speed>> speed) {
        this.nmeaMode = nmeaMode;
        this.gpsPosition = gpsPosition;
        this.track = track;
        this.speed = speed;
    }

    public GpsPosition getPosition() {
        return gpsPosition;
    }

    public Optional<Quantity<Angle>> getTrack() {
        return track;
    }

    public Optional<Quantity<Speed>> getSpeed() {
        return speed;
    }

    public boolean is3DFix() {
        return nmeaMode == NMEAMode.FIX_3D;
    }

    @Override
    public String toString() {
        return "GpsData{" +
                "gpsPosition=" + gpsPosition +
                ", track=" + track +
                ", speed=" + speed +
                '}';
    }
}

package com.cciocau.goose.sensor.gps;

import com.cciocau.goose.sensor.SensorData;
import com.cciocau.goose.sensor.gps.gpsd.NMEAMode;

import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Speed;
import java.time.ZonedDateTime;
import java.util.Optional;

public class GpsData implements SensorData {
    private final ZonedDateTime time;

    private final NMEAMode nmeaMode;
    private final GpsPosition gpsPosition;
    private final Optional<Quantity<Angle>> track;
    private final Optional<Quantity<Speed>> speed;

    public GpsData() {
        this(ZonedDateTime.now(), NMEAMode.UNKNOWN, new GpsPosition(), Optional.empty(), Optional.empty());
    }

    public GpsData(ZonedDateTime time, NMEAMode nmeaMode, GpsPosition gpsPosition, Optional<Quantity<Angle>> track, Optional<Quantity<Speed>> speed) {
        this.time = time;
        this.nmeaMode = nmeaMode;
        this.gpsPosition = gpsPosition;
        this.track = track;
        this.speed = speed;
    }

    public ZonedDateTime getTime() {
        return time;
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
                "time=" + time +
                ", nmeaMode=" + nmeaMode +
                ", gpsPosition=" + gpsPosition +
                ", track=" + track +
                ", speed=" + speed +
                '}';
    }
}

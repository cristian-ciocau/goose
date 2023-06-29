package com.cciocau.goose.data;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import java.util.Optional;

public class Aircraft {
    private final int icaoAddress;
    private final IcaoAircraftCategory icaoCategory;
    private final String callSign;

    private final Track track;

    private final Optional<Quantity<Speed>> speed;

    private final Position position;

    private final PressureAltitude pressureAltitude;

    public Aircraft(int icaoAddress, IcaoAircraftCategory icaoCategory, String callSign, Track track, Optional<Quantity<Speed>> speed, Position position) {
        this(icaoAddress, icaoCategory, callSign, track, speed, position, null);
    }

    public Aircraft(int icaoAddress, IcaoAircraftCategory icaoCategory, String callSign, Track track, Optional<Quantity<Speed>> speed, Position position, PressureAltitude pressureAltitude) {
        this.icaoAddress = icaoAddress;
        this.icaoCategory = icaoCategory;
        this.callSign = callSign;
        this.track = track;
        this.speed = speed;
        this.position = position;
        this.pressureAltitude = pressureAltitude;
    }

    public int getIcaoAddress() {
        return icaoAddress;
    }

    public IcaoAircraftCategory getIcaoCategory() {
        return icaoCategory;
    }

    public String getCallSign() {
        return callSign;
    }

    public Optional<Track> getTrack() {
        return Optional.ofNullable(track);
    }

    public Optional<Quantity<Speed>> getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return position;
    }

    public Optional<PressureAltitude> getPressureAltitude() {
        return Optional.ofNullable(pressureAltitude);
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "icaoAddress=" + icaoAddress +
                ", icaoCategory=" + icaoCategory +
                ", callSign='" + callSign + '\'' +
                ", track=" + track +
                ", speed=" + speed +
                ", position=" + position +
                ", pressureAltitude=" + pressureAltitude +
                '}';
    }
}

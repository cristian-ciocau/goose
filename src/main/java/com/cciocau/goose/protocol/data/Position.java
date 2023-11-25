package com.cciocau.goose.protocol.data;

import com.cciocau.goose.sensor.gps.GpsPosition;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;

public class Position {
    private final Quantity<Angle> latitude;
    private final Quantity<Angle> longitude;
    private final Quantity<Length> altitude;

    private final ComparableQuantity<Length> latitudeError;
    private final ComparableQuantity<Length> longitudeError;

    public Position(GpsPosition gpsPosition) {
        this(gpsPosition.getLatitude(), gpsPosition.getLongitude(), gpsPosition.getAltitude(), gpsPosition.getLatitudeError(), gpsPosition.getLongitudeError());
    }

    public Position(Quantity<Angle> latitude, Quantity<Angle> longitude, Quantity<Length> altitude, ComparableQuantity<Length> latitudeError, ComparableQuantity<Length> longitudeError) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.latitudeError = latitudeError;
        this.longitudeError = longitudeError;
    }

    public Quantity<Angle> getLatitude() {
        return latitude;
    }

    public Quantity<Angle> getLongitude() {
        return longitude;
    }

    public Quantity<Length> getAltitude() {
        return altitude;
    }

    public ComparableQuantity<Length> getLatitudeError() {
        return latitudeError;
    }

    public ComparableQuantity<Length> getLongitudeError() {
        return longitudeError;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", latitudeError=" + latitudeError +
                ", longitudeError=" + longitudeError +
                '}';
    }
}

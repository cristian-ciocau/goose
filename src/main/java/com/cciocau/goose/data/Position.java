package com.cciocau.goose.data;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class Position {
    private final double latitude;
    private final double longitude;
    private final Quantity<Length> altitude;

    private final ComparableQuantity<Length> latitudeError;
    private final ComparableQuantity<Length> longitudeError;

    public Position(double latitude, double longitude, Quantity<Length> altitude, ComparableQuantity<Length> latitudeError, ComparableQuantity<Length> longitudeError) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.latitudeError = latitudeError;
        this.longitudeError = longitudeError;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
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

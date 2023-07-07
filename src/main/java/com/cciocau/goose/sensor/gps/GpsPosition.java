package com.cciocau.goose.sensor.gps;

import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class GpsPosition {
    private final double latitude;
    private final double longitude;
    private final ComparableQuantity<Length> latitudeError;
    private final ComparableQuantity<Length> longitudeError;

    private final Quantity<Length> altitude;
    private final ComparableQuantity<Length> verticalError;

    public GpsPosition(double latitude, double longitude, ComparableQuantity<Length> latitudeError, ComparableQuantity<Length> longitudeError, Quantity<Length> altitude, ComparableQuantity<Length> verticalError) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeError = latitudeError;
        this.longitudeError = longitudeError;

        this.altitude = altitude;
        this.verticalError = verticalError;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ComparableQuantity<Length> getLatitudeError() {
        return latitudeError;
    }

    public ComparableQuantity<Length> getLongitudeError() {
        return longitudeError;
    }

    public Quantity<Length> getAltitude() {
        return altitude;
    }

    public ComparableQuantity<Length> getVerticalError() {
        return verticalError;
    }

    @Override
    public String toString() {
        return "GpsPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", latitudeError=" + latitudeError +
                ", longitudeError=" + longitudeError +
                ", altitude=" + altitude +
                ", verticalError=" + verticalError +
                '}';
    }
}

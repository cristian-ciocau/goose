package com.cciocau.goose.gps;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class GpsPosition {
    private final double latitude;
    private final double longitude;
    private final Quantity<Length> altitude;
    private final int accuracy;

    public GpsPosition(double latitude, double longitude, Quantity<Length> altitude, int accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
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

    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "GpsPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", accuracy=" + accuracy +
                '}';
    }
}

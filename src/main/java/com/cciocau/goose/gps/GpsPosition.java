package com.cciocau.goose.gps;

public class GpsPosition {
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final int accuracy;

    public GpsPosition(double latitude, double longitude, double altitude, int accuracy) {
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

    public double getAltitude() {
        return altitude;
    }

    public int getAltitudeFeet() {
        return (int) (altitude * 3.28084D);
    }

    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", accuracy=" + accuracy +
                '}';
    }
}

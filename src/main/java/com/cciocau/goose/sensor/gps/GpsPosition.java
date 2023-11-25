package com.cciocau.goose.sensor.gps;

import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;

public class GpsPosition {
    private final Quantity<Angle> latitude;
    private final Quantity<Angle> longitude;
    private final ComparableQuantity<Length> latitudeError;
    private final ComparableQuantity<Length> longitudeError;

    private final Quantity<Length> altitude;
    private final ComparableQuantity<Length> verticalError;

    public GpsPosition() {
        this(
                Quantities.getQuantity(0, USCustomary.DEGREE_ANGLE),
                Quantities.getQuantity(0, USCustomary.DEGREE_ANGLE),
                Quantities.getQuantity(0, Units.METRE),
                Quantities.getQuantity(0, Units.METRE),
                Quantities.getQuantity(0, Units.METRE),
                Quantities.getQuantity(0, Units.METRE)
        );
    }

    public GpsPosition(Quantity<Angle> latitude, Quantity<Angle> longitude, ComparableQuantity<Length> latitudeError, ComparableQuantity<Length> longitudeError, Quantity<Length> altitude, ComparableQuantity<Length> verticalError) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeError = latitudeError;
        this.longitudeError = longitudeError;

        this.altitude = altitude;
        this.verticalError = verticalError;
    }

    public Quantity<Angle> getLatitude() {
        return latitude;
    }

    public Quantity<Angle> getLongitude() {
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

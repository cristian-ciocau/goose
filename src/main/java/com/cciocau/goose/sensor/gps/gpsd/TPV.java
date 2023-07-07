package com.cciocau.goose.sensor.gps.gpsd;

import com.google.gson.annotations.SerializedName;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import java.util.Optional;

public class TPV extends GpsdResponse {

    // Latitude in degrees: +/- signifies North/South.
    @SerializedName("lat")
    private double lat;

    // Longitude in degrees: +/- signifies East/West.
    @SerializedName("lon")
    private double lon;

    // Latitude error estimate in meters. Certainty unknown.
    @SerializedName("epx")
    private double estimatedLatitudeError;

    // Longitude error estimate in meters. Certainty unknown.
    @SerializedName("epy")
    private double estimatedLongitudeError;

    // Deprecated. Undefined. Use altHAE.
    @SerializedName("alt")
    private double alt;

    //Altitude, height above ellipsoid, in meters. Probably WGS84.
    @SerializedName("altHAE")
    private Double altHAE;

    // Estimated vertical error in meters. Certainty unknown.
    @SerializedName("epv")
    private double estimatedVerticalError;

    // Climb (positive) or sink (negative) rate, meters per second.
    @SerializedName("climb")
    private double climbRate;

    // Course over ground, degrees from true north.
    @SerializedName("track")
    private Double track;

    // Speed over ground, meters per second.
    @SerializedName("speed")
    private Double speed;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public ComparableQuantity<Length> getLatError() {
        return Quantities.getQuantity(estimatedLatitudeError, Units.METRE);
    }

    public ComparableQuantity<Length> getLonError() {
        return Quantities.getQuantity(estimatedLongitudeError, Units.METRE);
    }

    public Quantity<Length> getAltitude() {
        return Quantities.getQuantity(getAltitudeMeters(), Units.METRE);
    }

    private double getAltitudeMeters() {
        return Optional.ofNullable(altHAE).orElse(alt);
    }

    public ComparableQuantity<Length> getVerticalError() {
        return Quantities.getQuantity(estimatedVerticalError, Units.METRE);
    }

    public double getClimbRate() {
        return climbRate;
    }

    public Optional<Double> getTrack() {
        return Optional.ofNullable(track);
    }

    public Optional<Quantity<Speed>> getSpeed() {
        return Optional.ofNullable(speed)
                .map(amount -> Quantities.getQuantity(amount, Units.METRE_PER_SECOND));
    }
}

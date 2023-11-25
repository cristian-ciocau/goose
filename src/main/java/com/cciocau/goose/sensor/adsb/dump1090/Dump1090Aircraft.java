package com.cciocau.goose.sensor.adsb.dump1090;

import com.google.gson.annotations.SerializedName;
import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;

import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import java.util.Objects;

public class Dump1090Aircraft {

    @SerializedName("hex")
    private String icaoHexId;

    @SerializedName("flight")
    private String callSign;

    @SerializedName("alt_baro")
    private int barometricAltitude;

    @SerializedName("alt_geo")
    private int gpsAltitude;

    @SerializedName("gs")
    private double groundSpeed;

    @SerializedName("track")
    private double track;

    @SerializedName("squawk")
    private String squawk;

    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lon")
    private Double longitude;

    @SerializedName("nac_p")
    private int horizontalNAC;

    @SerializedName("nac_v")
    private int verticalNAC;

    public String getIcaoHexId() {
        return icaoHexId;
    }

    public String getCallSign() {
        return callSign;
    }

    public ComparableQuantity<Length> getBarometricAltitude() {
        return Quantities.getQuantity(barometricAltitude, USCustomary.FOOT);
    }

    public ComparableQuantity<Length> getGpsAltitude() {
        return Quantities.getQuantity(gpsAltitude, USCustomary.FOOT);
    }

    public ComparableQuantity<Speed> getGroundSpeed() {
        return Quantities.getQuantity(groundSpeed, USCustomary.KNOT);
    }

    public ComparableQuantity<Angle> getTrack() {
        return Quantities.getQuantity(track, USCustomary.DEGREE_ANGLE);
    }

    public String getSquawk() {
        return squawk;
    }

    public ComparableQuantity<Angle> getLatitude() {
        return Quantities.getQuantity(latitude, USCustomary.DEGREE_ANGLE);
    }

    public ComparableQuantity<Angle> getLongitude() {
        return Quantities.getQuantity(longitude, USCustomary.DEGREE_ANGLE);
    }

    public int getHorizontalNAC() {
        return horizontalNAC;
    }

    public int getVerticalNAC() {
        return verticalNAC;
    }

    public boolean valid() {
        return Objects.nonNull(latitude) && Objects.nonNull(longitude);
    }
}

package com.cciocau.goose.sensor.adsb.dump1090;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;

public class Dump1090 {
    @SerializedName("now")
    private double now;

    @SerializedName("aircraft")
    private List<Dump1090Aircraft> aircraftList;

    public Instant getNow() {
        return Instant.ofEpochSecond((long) now);
    }

    public List<Dump1090Aircraft> getAircraftList() {
        return aircraftList;
    }
}

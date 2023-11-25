package com.cciocau.goose.sensor.gps.gpsd;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public class GpsdResponse {
    @SerializedName("class")
    private String responseClass;

    // Name of the originating device.
    @SerializedName("device")
    private String device;

    public String getResponseClass() {
        return responseClass;
    }

    public Optional<String> getDevice() {
        return Optional.ofNullable(device);
    }
}

package com.cciocau.goose.sensor.gps.gpsd;

import com.google.gson.annotations.SerializedName;

public class GpsdResponse {
    @SerializedName("class")
    private String responseClass;

    @SerializedName("device")
    private String device;

    public String getResponseClass() {
        return responseClass;
    }

    public String getDevice() {
        return device;
    }
}

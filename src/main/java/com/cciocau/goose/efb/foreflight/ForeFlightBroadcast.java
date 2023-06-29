package com.cciocau.goose.efb.foreflight;

import com.google.gson.annotations.SerializedName;

public class ForeFlightBroadcast {

    @SerializedName("App")
    private final String applicationName;

    @SerializedName("GDL90")
    private final GDL90Config gdl90Configuration;

    public ForeFlightBroadcast(String applicationName, GDL90Config gdl90Configuration) {
        this.applicationName = applicationName;
        this.gdl90Configuration = gdl90Configuration;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public GDL90Config getGdl90Configuration() {
        return gdl90Configuration;
    }
}

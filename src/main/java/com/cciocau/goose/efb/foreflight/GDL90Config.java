package com.cciocau.goose.efb.foreflight;

import com.google.gson.annotations.SerializedName;

public class GDL90Config {
    @SerializedName("port")
    private final int port;

    public GDL90Config(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}

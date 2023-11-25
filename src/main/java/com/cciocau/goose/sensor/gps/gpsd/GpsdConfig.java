package com.cciocau.goose.sensor.gps.gpsd;

public class GpsdConfig {
    private String host = "localhost";
    private int port = 2947;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

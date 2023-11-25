package com.cciocau.goose.sensor.gps;

import java.time.Duration;

public class GpsConfig {
    private Duration fixValidDuration = Duration.ofSeconds(10);

    public Duration getFixValidDuration() {
        return fixValidDuration;
    }

    public void setFixValidDuration(Duration fixValidDuration) {
        this.fixValidDuration = fixValidDuration;
    }
}

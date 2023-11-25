package com.cciocau.goose.sensor.adsb.dump1090;

import java.nio.file.Path;
import java.time.Duration;

public class Dump1090Config {
    private Path aircraftFilePath = Path.of("/home/pi/Applications/dump1090-json/aircraft.json");
    private Duration eventTTL = Duration.ofSeconds(30);

    public Path getAircraftFilePath() {
        return aircraftFilePath;
    }

    public void setAircraftFilePath(Path aircraftFilePath) {
        this.aircraftFilePath = aircraftFilePath;
    }

    public Duration getEventTTL() {
        return eventTTL;
    }

    public void setEventTTL(Duration eventTTL) {
        this.eventTTL = eventTTL;
    }
}

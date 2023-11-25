package com.cciocau.goose.sensor.adsb.dump1090;

import java.nio.file.Path;
import java.time.Duration;

public class Dump1090Config {
    private Path aircraftFilePath = Path.of("/home/pi/Applications/dump1090-json/aircraft.json");

    // "aircraft.json" is timestamped. In case Dump1090 crashes and never updates the JSON file, how long to wait
    // to discard the JSON.
    private Duration eventTTL = Duration.ofSeconds(30);

    // how long to wait before discarding a target that has been "lost" by the RTL receiver
    private Duration aircraftMaxLastSeen = Duration.ofSeconds(30);

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

    public Duration getAircraftMaxLastSeen() {
        return aircraftMaxLastSeen;
    }

    public void setAircraftMaxLastSeen(Duration aircraftMaxLastSeen) {
        this.aircraftMaxLastSeen = aircraftMaxLastSeen;
    }
}

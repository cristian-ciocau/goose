package com.cciocau.goose.sensor.gps;

import java.io.IOException;
import java.util.stream.Stream;

public interface GPSReader {
    Stream<GpsData> read() throws IOException;
}

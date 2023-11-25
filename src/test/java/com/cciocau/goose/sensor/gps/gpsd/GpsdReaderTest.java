package com.cciocau.goose.sensor.gps.gpsd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GpsdReaderTest {
    private static final String CRLF = "\r\n";

    @Test
    @DisplayName("Can consume empty input")
    void emptyInput() throws IOException {
        var input = "";

        var reader = new GpsdReader(input);
        var data = reader.read().collect(Collectors.toList());

        assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Invalid data is ignored")
    void invalid() throws IOException {
        var input = "INVALID";

        var reader = new GpsdReader(input);
        var data = reader.read().collect(Collectors.toList());

        assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Only TPV (Time-Position-Velocity) reports are accepted")
    void tpvOnly() throws IOException {
        var tpv = "{\"class\":\"TPV\"}";
        var sky = "{\"class\":\"SKY\"}";
        var input = String.join(CRLF, tpv, sky);

        var reader = new GpsdReader(input);
        var data = reader.read().collect(Collectors.toList());

        assertEquals(1, data.size());
    }
}
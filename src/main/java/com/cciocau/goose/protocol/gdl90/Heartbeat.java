package com.cciocau.goose.protocol.gdl90;

import java.time.Instant;

public class Heartbeat {

    public byte[] generate() {
        byte[] msg = new byte[7];
        msg[0] = 0;
        Bytes.writeByte(msg, 1, "10000001");
        Instant instant = Instant.now();
        long secondsSinceMidnightUTC = instant.getEpochSecond() % 86400L;
        msg[2] = (byte)((int)(secondsSinceMidnightUTC >> 16 << 7 | 1L));
        msg[3] = (byte)((int)(secondsSinceMidnightUTC & 255L));
        msg[4] = (byte)((int)((secondsSinceMidnightUTC & 65535L) >> 8));
        return msg;
    }

    @Override
    public String toString() {
        return "Heartbeat{}";
    }
}

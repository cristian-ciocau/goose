package com.cciocau.goose.protocol.gdl90;

public class OwnShipGeometricAltitude {
    private final int altitude;

    public OwnShipGeometricAltitude(int altitude) {
        this.altitude = altitude;
    }

    public byte[] generate() {
        byte[] msg = new byte[5];
        msg[0] = 11;

        int alt = altitude / 5;
        msg[1] = (byte)((alt >> 8) & 0xFF);
        msg[2] = (byte)(alt & 0xFF);

        // 10 meters accuracy
        msg[3] = (byte)Integer.toUnsignedLong(0x00);
        msg[4] = (byte)Integer.toUnsignedLong(0x0A);

        return msg;
    }

    @Override
    public String toString() {
        return "OwnShipGeometricAltitude{" +
                "altitude=" + altitude +
                '}';
    }
}

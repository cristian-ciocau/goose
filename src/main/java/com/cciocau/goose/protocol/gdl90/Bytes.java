package com.cciocau.goose.protocol.gdl90;

import java.nio.charset.StandardCharsets;

public class Bytes {

    public static void writeInt(byte[] destination, int offset, int length, int value) {
        for (int i = offset + length - 1, shiftOffset = 0; i >= offset; i--, shiftOffset++) {
            destination[i] = (byte)((value >>> 8 * shiftOffset) & 0xFF);
        }
    }

    public static void writeLong(byte[] destination, int offset, int length, long value) {
        for (int i = offset + length - 1, shiftOffset = 0; i >= offset; i--, shiftOffset++) {
            destination[i] = (byte)((value >>> 8 * shiftOffset) & 0xFF);
        }
    }

    public static void writeByte(byte[] destination, int offset, String eightBits) {
        int value = Integer.parseInt(eightBits, 2);

        writeInt(destination, offset, 1, value);
    }

    public static void writeString(byte[] destination, int offset, int length, String text) {
        var bytes = text.getBytes(StandardCharsets.UTF_8);

        int maxLength = Math.min(length, bytes.length);

        System.arraycopy(bytes, 0, destination, offset, maxLength);
    }
}

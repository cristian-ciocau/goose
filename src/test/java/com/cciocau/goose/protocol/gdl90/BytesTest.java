package com.cciocau.goose.protocol.gdl90;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BytesTest {

    @Test
    void one() {
        int value = 0x55404C21;

        var buff = new byte[1];

        Bytes.writeInt(buff, 0, 1, value);

        assertEquals(0x21, buff[0]);
    }

    @Test
    void two() {
        int value = 0x55404C21;

        var buff = new byte[2];

        Bytes.writeInt(buff, 0, 2, value);

        assertEquals(0x4C, buff[0]);
        assertEquals(0x21, buff[1]);
    }
    @Test
    void three() {
        int value = 0x55404C21;

        var buff = new byte[3];

        Bytes.writeInt(buff, 0, 3, value);

        assertEquals(0x40, buff[0]);
        assertEquals(0x4C, buff[1]);
        assertEquals(0x21, buff[2]);
    }

    @Test
    void four() {
        int value = 0x55404C21;

        var buff = new byte[4];

        Bytes.writeInt(buff, 0, 4, value);

        assertEquals(0x55, buff[0]);
        assertEquals(0x40, buff[1]);
        assertEquals(0x4C, buff[2]);
        assertEquals(0x21, buff[3]);
    }

    @Test
    void eightBit() {
        var eightBit = "10000001";

        var buff = new byte[1];
        Bytes.writeByte(buff, 0, eightBit);

        assertEquals(-127, buff[0]);
    }

    @Test
    void string() {
        var text = "Hello";

        var buff = new byte[8];

        Bytes.writeString(buff, 0, 8, text);

        assertEquals('H', buff[0]);
        assertEquals('e', buff[1]);
        assertEquals('l', buff[2]);
        assertEquals('l', buff[3]);
        assertEquals('o', buff[4]);
        assertEquals(0, buff[5]);
        assertEquals(0, buff[6]);
        assertEquals(0, buff[7]);
    }
}
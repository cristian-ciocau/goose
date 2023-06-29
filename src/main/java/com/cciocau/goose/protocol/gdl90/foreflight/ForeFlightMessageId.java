package com.cciocau.goose.protocol.gdl90.foreflight;

import com.cciocau.goose.protocol.gdl90.Bytes;

import java.io.Serializable;

public class ForeFlightMessageId implements Serializable {
    private final String name;
    private final String longName;

    private final long serialNumber;

    public ForeFlightMessageId(String name) {
        this(name, name,  0x1122334455667788L);
    }

    public ForeFlightMessageId(String name, String longName, long serialNumber) {
        this.name = name;
        this.longName = longName;
        this.serialNumber = serialNumber;
    }

    public byte[] generate() {
        byte[] msg = new byte[39];
        Bytes.writeInt(msg, 0, 1, 0x65);
        msg[1] = 0;
        msg[2] = 1;

        Bytes.writeLong(msg, 3, 8, serialNumber);

        Bytes.writeString(msg, 11, 8, name);
        Bytes.writeString(msg, 19, 16, longName);

        msg[35] = 1;
        msg[36] = 1;
        msg[37] = 1;
        msg[38] = 1;

        return msg;
    }

    @Override
    public String toString() {
        return "ForeFlightMessageId{" +
                "name='" + name + '\'' +
                ", longName='" + longName + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}

package com.cciocau.goose.protocol.gdl90;

import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class OwnShipGeometricAltitude {
    private final Quantity<Length> altitude;
    private final ComparableQuantity<Length> verticalError;

    public OwnShipGeometricAltitude(Quantity<Length> altitude, ComparableQuantity<Length> verticalError) {
        this.altitude = altitude;
        this.verticalError = verticalError;
    }

    public byte[] generate() {
        byte[] msg = new byte[5];
        msg[0] = 11;

        int alt = altitude.to(USCustomary.FOOT).getValue().intValue() / 5;
        msg[1] = (byte)((alt >> 8) & 0xFF);
        msg[2] = (byte)(alt & 0xFF);

        // VFOM
        var vfom = new VFOM(verticalError).toBytes();
        msg[3] = vfom[0];
        msg[4] = vfom[1];

        return msg;
    }

    @Override
    public String toString() {
        return "OwnShipGeometricAltitude{" +
                "altitude=" + altitude +
                '}';
    }
}

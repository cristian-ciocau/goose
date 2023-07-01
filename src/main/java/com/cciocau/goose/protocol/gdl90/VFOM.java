package com.cciocau.goose.protocol.gdl90;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Length;

public class VFOM {
    private static final ComparableQuantity<Length> MAX_VFOM_ERROR = Quantities.getQuantity(32766, Units.METRE);

    private final boolean warning;
    private final ComparableQuantity<Length> verticalError;

    // TODO unavailable VFOM is not implemented yet
    public VFOM(ComparableQuantity<Length> verticalError) {
        this(false, verticalError);
    }

    public VFOM(boolean warning, ComparableQuantity<Length> verticalError) {
        this.warning = warning;
        this.verticalError = verticalError;
    }

    public byte[] toBytes() {
        long vfom;

        if (verticalError.isGreaterThanOrEqualTo(MAX_VFOM_ERROR)) {
            vfom = 0x7FFE;

        } else {
            vfom = verticalError.to(Units.METRE).getValue().longValue();
        }

        if (warning) {
            vfom = vfom | 0x8000;
        }

        byte[] result = new byte[2];
        Bytes.writeLong(result, 0, 2, vfom);

        return result;
    }
}

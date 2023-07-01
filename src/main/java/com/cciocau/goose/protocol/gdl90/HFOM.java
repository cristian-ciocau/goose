package com.cciocau.goose.protocol.gdl90;

import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.unit.Units;

import javax.measure.quantity.Length;

public enum HFOM {
    UNKNOWN(0),
    LESS_10_NM(1),
    LESS_4_NM(2),
    LESS_2_NM(3),
    LESS_1_NM(4),
    LESS_0_5_NM(5),
    LESS_0_3_NM(6),
    LESS_0_1_NM(7),
    LESS_0_0_5_NM(8),
    LESS_30_M(9),
    LESS_10_M(10),
    LESS_3_M(11);

    private final int value;

    HFOM(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HFOM from(ComparableQuantity<Length> error) {
        var nauticalMiles = error.to(USCustomary.NAUTICAL_MILE).getValue().doubleValue();
        var meters = error.to(Units.METRE).getValue().doubleValue();

        if (lessThan(meters, 3)) {
            return LESS_3_M;

        } else if (lessThan(meters, 10)) {
            return LESS_10_M;

        } else if (lessThan(meters, 30)) {
            return LESS_30_M;

        } else if (lessThan(nauticalMiles, 0.05)) {
            return LESS_0_0_5_NM;

        } else if (lessThan(nauticalMiles, 0.1)) {
            return LESS_0_1_NM;

        } else if (lessThan(nauticalMiles, 0.3)) {
            return LESS_0_3_NM;

        } else if (lessThan(nauticalMiles, 0.5)) {
            return LESS_0_5_NM;

        } else if (lessThan(nauticalMiles, 1)) {
            return LESS_1_NM;

        } else if (lessThan(nauticalMiles, 2)) {
            return LESS_2_NM;

        } else if (lessThan(nauticalMiles, 4)) {
            return LESS_4_NM;

        } else if (lessThan(nauticalMiles, 10)) {
            return LESS_10_NM;

        } else {
            return UNKNOWN;
        }
    }

    private static boolean lessThan(double input, double operand) {
        return input < operand;
    }
}

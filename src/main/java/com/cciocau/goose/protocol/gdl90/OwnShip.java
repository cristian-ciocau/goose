package com.cciocau.goose.protocol.gdl90;

import com.cciocau.goose.data.Aircraft;
import com.cciocau.goose.data.Track;
import com.cciocau.goose.data.TrackType;
import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;

import javax.measure.Quantity;

public class OwnShip implements Message {

    private final Aircraft aircraft;


    public OwnShip(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    private static byte[] makeLatLng(double v) {
        byte[] ret = new byte[3];
        v /= 2.1457672E-5;
        int wk = (int)Math.round(v);
        ret[0] = (byte)((wk & 16711680) >> 16);
        ret[1] = (byte)((wk & '\uff00') >> 8);
        ret[2] = (byte)(wk & 255);
        return ret;
    }

    public byte[] generate() {
        byte[] msg = new byte[28];
        msg[0] = 10;
        msg[1] = 0;

        Bytes.writeInt(msg, 2, 3, aircraft.getIcaoAddress());

        byte[] tmp = makeLatLng(aircraft.getPosition().getLatitude());
        msg[5] = tmp[0];
        msg[6] = tmp[1];
        msg[7] = tmp[2];
        tmp = makeLatLng(aircraft.getPosition().getLongitude());
        msg[8] = tmp[0];
        msg[9] = tmp[1];
        msg[10] = tmp[2];

//        int alt;
//        if (altitude >= -1000 && altitude <= 101350) {
//            alt = altitude / 25 + 40;
//        } else {
//            alt = 4095;
//        }
//
//        msg[11] = (byte)((alt & 4080) >> 4);
//        msg[12] = (byte)((alt & 15) << 4);

        msg[11] = (byte)Integer.toUnsignedLong(0xFF);
        msg[12] = (byte)Integer.toUnsignedLong(0xF0);

        aircraft.getSpeed()
                .map(quantity -> quantity.to(USCustomary.KNOT))
                .map(Quantity::getValue)
                .map(Number::intValue)
                .filter(value -> value > 30)
                .ifPresent(value -> msg[12] = (byte)(Byte.toUnsignedInt(msg[12]) | 8));

        // TODO - implement Heading (Magnetic or True)
        aircraft.getTrack()
                .filter(track -> track.getType() == TrackType.TRUE)
                .ifPresent(value -> msg[12] = (byte)(Byte.toUnsignedInt(msg[12]) | 1));

        var horizontalPositionError = min(aircraft.getPosition().getLatitudeError(), aircraft.getPosition().getLongitudeError());

        var hpl = 0xB0;
        var hfom = HFOM.from(horizontalPositionError).getValue();

        msg[13] = (byte) Integer.toUnsignedLong(hpl | hfom);

        aircraft.getSpeed()
                .map(quantity -> quantity.to(USCustomary.KNOT))
                .map(Quantity::getValue)
                .map(Number::intValue)
                .ifPresentOrElse(speed -> {
            msg[14] = (byte)((speed & 4080) >> 4);
            msg[15] = (byte)((speed & 15) << 4);
        }, () -> {
            msg[14] = (byte)Integer.toUnsignedLong(0xFF);
            msg[15] = (byte)Integer.toUnsignedLong(0xF0);
        });

//        int vvel = a.getVerticalSpeed() / 64;
//        msg[15] |= (byte)((vvel & 3840) >> 8);
//        msg[16] = (byte)(vvel & 255);

        msg[15] = (byte)(Byte.toUnsignedInt(msg[15]) | 8);
        msg[16] = (byte)Integer.toUnsignedLong(0X00);

        var trackOrHeading = aircraft.getTrack()
                .map(Track::getValue)
                .orElse(0);

        // Track / Heading
        msg[17] = (byte)((int)((double) trackOrHeading / 1.40625));

        // Emitter Category
        msg[18] = 1;

        String s = aircraft.getCallSign();

        for(int i = 0; i < s.length() && i < 8; ++i) {
            char c = s.charAt(i);
            if (c != 20 && (c < '0' || c > '9') && (c < 'A' || c > 'Z') && c != 'e' && c != 'u' && c != 'a' && c != 'r' && c != 't') {
                c = ' ';
            }

            msg[19 + i] = (byte)c;
        }

        msg[27] = 0;
        return msg;
    }

    private <T extends Quantity<T>> ComparableQuantity<T> min(ComparableQuantity<T> one, ComparableQuantity<T> two) {
        if (one.isLessThan(two)) {
            return one;

        } else {
            return two;
        }
    }

    @Override
    public String toString() {
        return "OwnShip{" +
                "aircraft=" + aircraft +
                '}';
    }
}

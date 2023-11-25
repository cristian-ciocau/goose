package com.cciocau.goose.sensor.gps.gpsd;

import com.cciocau.goose.sensor.gps.GPSReader;
import com.cciocau.goose.sensor.gps.GpsData;
import com.cciocau.goose.sensor.gps.GpsPosition;
import com.google.gson.Gson;
import io.vavr.control.Either;

import java.io.*;
import java.util.stream.Stream;

public class GpsdReader implements GPSReader {
    private final Gson gson = new Gson();
    private final InputStream inputStream;

    GpsdReader(String input) {
        this.inputStream = new ByteArrayInputStream(input.getBytes());
    }

    public GpsdReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Stream<GpsData> read() throws IOException {
        try {
            // this stream shall not be closed
            var stream = new BufferedReader(new InputStreamReader(inputStream));

            return stream.lines()
                    .map(line -> deserialize(line, TPV.class))
                    .filter(Either::isRight)
                    .map(Either::get)
                    .filter(tpv -> "TPV".equalsIgnoreCase(tpv.getResponseClass()))
                    .map(this::toGpsData);


        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }
    private <T> Either<Exception, T> deserialize(String line, Class<T> type) {
    try {
        var generic = gson.fromJson(line, type);
        return Either.right(generic);

    } catch (Exception exception) {
        return Either.left(new Exception("failed to deserialize line: " + line, exception));
    }
}

    private GpsData toGpsData(TPV tpv) {
        var position = new GpsPosition(tpv.getLat(), tpv.getLon(), tpv.getLatError(), tpv.getLonError(), tpv.getAltitude(), tpv.getVerticalError());

        var track = tpv.getTrack();
        var speed = tpv.getSpeed();

        return new GpsData(tpv.getMode(), position, track, speed);
    }
}

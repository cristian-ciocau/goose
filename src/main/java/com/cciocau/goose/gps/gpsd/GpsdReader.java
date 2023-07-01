package com.cciocau.goose.gps.gpsd;

import com.cciocau.goose.gps.GPSReader;
import com.cciocau.goose.gps.GpsData;
import com.cciocau.goose.gps.GpsPosition;
import com.google.gson.Gson;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class GpsdReader implements GPSReader {
    private final Gson gson = new Gson();
    private final InputStream inputStream;

    public GpsdReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Stream<GpsData> read() throws IOException {
        try {
            // this stream shall not be closed
            var stream = new BufferedReader(new InputStreamReader(inputStream));

            return stream.lines()
                    .map(line -> deserialize(line, GpsdResponse.class))
                    .filter(tuple -> tuple._2().filter(generic -> generic.getResponseClass().equalsIgnoreCase("tpv")).isDefined())
                    .map(tuple -> tuple._2()
                            .filter(generic -> generic.getResponseClass().equalsIgnoreCase("tpv"))
                            .map(g -> deserialize(tuple._1(), TPV.class))
                            .map(Tuple2::_2)
                    )
                    .filter(Option::isDefined)
                    .map(Option::get)
                    .filter(Either::isRight)
                    .map(Either::get)
                    .map(this::toGpsData);

        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    private <T> Tuple2<String, Either<Exception, T>> deserialize(String line, Class<T> type) {
        try {
            var generic = gson.fromJson(line, type);

            return Tuple.of(line, Either.right(generic));
        } catch (Exception exception) {
            exception.printStackTrace();
            return Tuple.of(line, Either.left(exception));
        }
    }

    private GpsData toGpsData(TPV tpv) {
        var position = new GpsPosition(tpv.getLat(), tpv.getLon(), tpv.getAltitude(), tpv.getLatError(), tpv.getLonError());

        var track = tpv.getTrack().orElse(null);
        var speed = tpv.getSpeed();

        return new GpsData(position, track, speed);
    }
}

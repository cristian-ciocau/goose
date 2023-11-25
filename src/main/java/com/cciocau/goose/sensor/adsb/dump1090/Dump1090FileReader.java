package com.cciocau.goose.sensor.adsb.dump1090;

import com.google.gson.Gson;
import io.vavr.control.Either;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Dump1090FileReader {
    private final File file;
    private final Gson gson = new Gson();

    public Dump1090FileReader(File file) {
        this.file = file;
    }

    public Either<IOException, Dump1090> read() {
        try {
            var content = Files.readString(file.toPath());

            var dump1090 = gson.fromJson(content, Dump1090.class);

            return Either.right(dump1090);

        } catch (Exception exception) {
            return Either.left(new IOException(exception));
        }
    }
}

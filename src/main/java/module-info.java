module goose {
    requires com.google.gson;
    requires com.google.common;
    requires java.measure;
    requires io.vavr;
    requires si.uom.units;
    requires systems.uom.common;

    opens com.cciocau.goose.sensor.gps to com.google.gson;
    opens com.cciocau.goose.sensor.gps.gpsd to com.google.gson;
    opens com.cciocau.goose.sensor to com.google.gson;
}
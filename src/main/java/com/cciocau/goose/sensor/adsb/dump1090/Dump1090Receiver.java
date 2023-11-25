package com.cciocau.goose.sensor.adsb.dump1090;

import com.cciocau.goose.protocol.data.*;
import com.cciocau.goose.sensor.SensorManager;
import com.cciocau.goose.sensor.adsb.AdsbTraffic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

public class Dump1090Receiver {
    private static final Logger logger = LogManager.getLogger(Dump1090Receiver.class);

    private final Dump1090Config config;
    private final SensorManager<AdsbTraffic> sensorManager;

    public Dump1090Receiver(SensorManager<AdsbTraffic> sensorManager) {
        this(new Dump1090Config(), sensorManager);
    }

    public Dump1090Receiver(Dump1090Config config, SensorManager<AdsbTraffic> sensorManager) {
        this.config = config;
        this.sensorManager = sensorManager;
    }

    public void receive() {
        var fileReader = new Dump1090FileReader(config.getAircraftFilePath().toFile());

        fileReader.read()
                .peekLeft(exception -> logger.error("Failed to receive Traffic Information - {}", exception.getMessage()))
                .toOption()
                .filter(this::eventNotExpired)
                .map(this::toTraffic)
                .forEach(sensorManager::notify);
    }

    private boolean eventNotExpired(Dump1090 event) {
        return event.getNow()
                .plusSeconds(config.getEventTTL().toSeconds())
                .isAfter(Instant.now());
    }

    private AdsbTraffic toTraffic(Dump1090 event) {
        var aircraft = event.getAircraftList()
                .stream()
                .filter(Dump1090Aircraft::valid)
                .filter(dumpAircraft -> dumpAircraft.isObservedRecently(config.getAircraftMaxLastSeen()))
                .map(this::aircraft)
                .collect(Collectors.toList());

        return new AdsbTraffic(aircraft);
    }

    private Aircraft aircraft(Dump1090Aircraft aircraft) {
        // TODO change position to take HFOM instead of Lat / Lon errors
        var latLonError = Quantities.getQuantity(0, Units.METRE);

        var position = new Position(aircraft.getLatitude(), aircraft.getLongitude(), aircraft.getGpsAltitude(), latLonError, latLonError);

        var hexId = Integer.parseInt(aircraft.getIcaoHexId(), 16);

        var aircraftCategory = IcaoAircraftCategory.NO_TYPE_INFORMATION;

        var callSign = Optional.ofNullable(aircraft.getCallSign())
                .orElse("");

        var track = new Track(TrackType.TRUE, aircraft.getTrack());

        return new Aircraft(hexId, aircraftCategory, callSign, track, Optional.of(aircraft.getGroundSpeed()), position, aircraft.getBarometricAltitude());
    }
}

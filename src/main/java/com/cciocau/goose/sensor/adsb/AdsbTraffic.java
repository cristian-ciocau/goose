package com.cciocau.goose.sensor.adsb;

import com.cciocau.goose.protocol.data.Aircraft;
import com.cciocau.goose.sensor.SensorData;

import java.util.List;

public class AdsbTraffic implements SensorData {
    private final List<Aircraft> aircraftList;

    public AdsbTraffic(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    @Override
    public String toString() {
        return "AdsbTraffic{" +
                "aircraftList=" + aircraftList +
                '}';
    }
}

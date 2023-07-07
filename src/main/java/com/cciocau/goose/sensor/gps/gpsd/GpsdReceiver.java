package com.cciocau.goose.sensor.gps.gpsd;

import com.cciocau.goose.sensor.SensorManager;
import com.cciocau.goose.sensor.gps.GpsData;

import java.net.Socket;

public class GpsdReceiver {
    private static final String HOST = "localhost";
//    private static final String HOST = "192.168.10.1";
    private static final int PORT = 2947;

    private final SensorManager<GpsData> sensorManager;

    public GpsdReceiver(SensorManager<GpsData> sensorManager) {
        this.sensorManager = sensorManager;
    }

    public void receive() {
        try (var socket = new Socket(HOST, PORT)) {
            socket.getOutputStream().write("?WATCH={\"enable\":true,\"json\":true}\r\n".getBytes());

            var input = socket.getInputStream();

            var reader = new GpsdReader(input);
            var stream = reader.read();

            stream.forEach(sensorManager::notify);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

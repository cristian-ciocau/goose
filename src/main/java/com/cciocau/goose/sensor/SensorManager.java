package com.cciocau.goose.sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorManager<T extends SensorData> {
    private final List<SensorConsumer<T>> listeners = new ArrayList<>();

    public void subscribe(SensorConsumer<T> listener) {
        listeners.add(listener);
    }

    public void unsubscribe(SensorConsumer<T> listener) {
        listeners.remove(listener);
    }

    public void notify(T data) {
        for (var listener : listeners) {
            listener.accept(data);
        }
    }
}

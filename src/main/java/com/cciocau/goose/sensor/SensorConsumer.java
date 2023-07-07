package com.cciocau.goose.sensor;

import java.util.function.Consumer;

public interface SensorConsumer<T extends SensorData> extends Consumer<T> {

}

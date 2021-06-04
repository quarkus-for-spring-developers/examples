package org.acme;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

import java.util.LinkedList;

public class RegisterMeterRegistry {

    public final MeterRegistry registry;
    public LinkedList<Long> list = new LinkedList<>();

    RegisterMeterRegistry(MeterRegistry registry) {
        this.registry = registry;
        registry.gaugeCollectionSize("monitoring.list.size", Tags.empty(), list);
    }
}

package org.acme.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Broadcast;

/**
 * A bean consuming data from the "prices" Kafka topic and applying some conversion.
 * The result is pushed to the "my-data-stream" stream which is an in-memory stream.
 */
@ApplicationScoped
public class PriceConverter {
    static final double CONVERSION_RATE = 0.88;

    // Consume from the `prices` channel and produce to the `my-data-stream` channel
    @Incoming("prices")
    @Outgoing("my-data-stream")

    // Send to all subscribers and Acknowledge the messages before calling this method
    @Broadcast
    public double process(int priceInUsd) {
        return priceInUsd * CONVERSION_RATE;
    }

}

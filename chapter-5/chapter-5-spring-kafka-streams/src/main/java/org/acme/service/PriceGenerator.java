package org.acme.service;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component("generateprice")
public class PriceGenerator implements Supplier<Flux<Integer>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceGenerator.class);
	private Random random = new Random();

	@Override
	public Flux<Integer> get() {
		return Flux.interval(Duration.ofSeconds(5))
			.onBackpressureDrop()
			.doOnNext(tick -> LOGGER.debug("Got tick: {}", tick))
			.map(tick -> this.random.nextInt(100))
			.doOnNext(random -> LOGGER.debug("Got random: {}", random));
	}
}

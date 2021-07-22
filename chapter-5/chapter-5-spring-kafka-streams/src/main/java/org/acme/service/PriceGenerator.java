package org.acme.service;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component("generateprice")
public class PriceGenerator implements Supplier<Flux<Integer>> {
	private final Random random = new Random();

	@Override
	public Flux<Integer> get() {
		return Flux.interval(Duration.ofSeconds(5))
			.onBackpressureDrop()
			.map(tick -> this.random.nextInt(100));
	}
}

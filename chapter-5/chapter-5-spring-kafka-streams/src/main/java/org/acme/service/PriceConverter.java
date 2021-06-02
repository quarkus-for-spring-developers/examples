package org.acme.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component("priceconverter")
public class PriceConverter implements Consumer<Flux<Integer>> {
	static final double CONVERSION_RATE = 0.88;

	private final InMemoryChannel<Double> inMemoryChannel;

	public PriceConverter(InMemoryChannel<Double> inMemoryChannel) {
		this.inMemoryChannel = inMemoryChannel;
	}

	@Override
	public void accept(Flux<Integer> priceInUsd) {
		priceInUsd
			.map(price -> price * CONVERSION_RATE)
			.subscribe(this.inMemoryChannel::emitValue);
	}
}

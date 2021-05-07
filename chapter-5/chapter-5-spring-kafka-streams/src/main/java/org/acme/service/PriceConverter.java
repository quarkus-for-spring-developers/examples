package org.acme.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component("priceconverter")
public class PriceConverter implements Consumer<Flux<Integer>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceConverter.class);
	static final double CONVERSION_RATE = 0.88;

	private final InMemoryChannel<Double> inMemoryChannel;

	public PriceConverter(InMemoryChannel<Double> inMemoryChannel) {
		this.inMemoryChannel = inMemoryChannel;
	}

	@Override
	public void accept(Flux<Integer> priceInUsd) {
		priceInUsd
			.doOnNext(price -> LOGGER.debug("priceInUsd = {}", price))
			.map(price -> price * CONVERSION_RATE)
			.doOnNext(convertedPrice -> LOGGER.debug("convertedPrice = {}", convertedPrice))
			.subscribe(this.inMemoryChannel::emitValue);
	}
}

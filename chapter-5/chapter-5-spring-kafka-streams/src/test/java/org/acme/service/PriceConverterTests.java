package org.acme.service;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

class PriceConverterTests {
	@Test
	public void emitsToSinkProperly() {
		InMemoryChannel<Double> inMemoryChannel = new InMemoryChannel<>();

		Flux<Integer> integerFlux = Flux.just(1, 2)
			.delayElements(Duration.ofSeconds(1));

		PublisherProbe<Integer> probe = PublisherProbe.of(integerFlux);
		new PriceConverter(inMemoryChannel).accept(probe.flux());

		StepVerifier.create(Flux.from(inMemoryChannel.getPublisher()).take(2))
			.expectNext(1 * PriceConverter.CONVERSION_RATE)
			.expectNext(2 * PriceConverter.CONVERSION_RATE)
			.expectComplete()
			.verify(Duration.ofSeconds(5));

		probe.assertWasSubscribed();
		probe.assertWasNotCancelled();
	}
}

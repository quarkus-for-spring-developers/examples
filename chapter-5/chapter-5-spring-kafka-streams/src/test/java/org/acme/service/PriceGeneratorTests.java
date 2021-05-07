package org.acme.service;

import java.time.Duration;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class PriceGeneratorTests {
	PriceGenerator generator = new PriceGenerator();

	private static final Predicate<Integer> VALUE_TEST =
		value -> (value >= 0) && (value < 100);

	@Test
	public void generatesProperly() {
		StepVerifier
			.withVirtualTime(() -> this.generator.get().take(2))
			.expectSubscription()
			.expectNoEvent(Duration.ofSeconds(5))
			.expectNextMatches(VALUE_TEST)
			.thenAwait(Duration.ofSeconds(5))
			.expectNextMatches(VALUE_TEST)
			.expectComplete()
			.verify(Duration.ofSeconds(15));
	}
}

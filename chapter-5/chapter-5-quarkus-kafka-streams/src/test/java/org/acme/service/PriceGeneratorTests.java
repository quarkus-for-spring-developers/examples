package org.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.helpers.test.AssertSubscriber;

class PriceGeneratorTests {
	PriceGenerator priceGenerator = new PriceGenerator();

	private static final Predicate<Integer> VALUE_TEST =
		value -> (value >= 0) && (value < 100);

	@Test
	public void generatesProperly() {
		List<Integer> prices = this.priceGenerator.generate()
			.select().first(2)
			.subscribe().withSubscriber(AssertSubscriber.create(2))
			.assertSubscribed()
			.awaitNextItem(Duration.ofSeconds(7)) // Needs to be a second or 2 more than the timing of actual events
			.awaitNextItem(Duration.ofSeconds(7))
			.awaitCompletion(Duration.ofSeconds(15))
			.assertCompleted()
			.getItems();

		assertThat(prices)
			.hasSize(2)
			.allMatch(VALUE_TEST);
	}
}

package org.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.helpers.test.AssertSubscriber;

class PriceGeneratorTests {
	PriceGenerator priceGenerator = new PriceGenerator();

	@Test
	public void generatesProperly() {
		List<Integer> prices = this.priceGenerator.generate()
			.select().first(2)
			.subscribe().withSubscriber(AssertSubscriber.create(2))
			.assertSubscribed()
			.awaitNextItem(Duration.ofSeconds(10)) // Needs to be a few seconds more than the timing of actual events
			.awaitNextItem(Duration.ofSeconds(10))
			.awaitCompletion(Duration.ofSeconds(15))
			.assertCompleted()
			.getItems();

		assertThat(prices)
			.hasSize(2)
			.allMatch(value -> (value >= 0) && (value < 100));
	}
}

package org.acme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.acme.TestTransaction;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FruitTests {
	@Test
	public void findByName() {
		Fruit fruit = TestTransaction.withRollback(() ->
			Fruit
				.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
				.replaceWith(Fruit.findByName("Grapefruit"))
		)
			.await()
			.atMost(Duration.ofSeconds(10));

		assertThat(fruit)
			.isNotNull()
			.extracting("name", "description")
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.id)
			.isNotNull()
			.isGreaterThan(2L);
	}
}

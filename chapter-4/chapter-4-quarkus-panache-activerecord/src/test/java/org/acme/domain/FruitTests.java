package org.acme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestTransaction
class FruitTests {
	@Test
	public void findByName() {
		Fruit.persist(new Fruit(null, "Grapefruit", "Summer fruit"));

		Optional<Fruit> fruit = Fruit.findByName("Grapefruit");
		assertThat(fruit)
			.isNotNull()
			.isPresent()
			.get()
			.extracting("name", "description")
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.get().id)
			.isNotNull()
			.isGreaterThan(2L);
	}
}

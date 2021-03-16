package org.acme.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
@TestTransaction
class FruitTests {
	@Test
	public void startingPointAsExpected() {
		List<Fruit> fruits = Fruit.listAll();

		assertThat(fruits)
			.hasSize(2)
			.extracting("name", "description")
			.containsExactlyInAnyOrder(
				tuple("Apple", "Hearty fruit"),
				tuple("Pear", "Juicy fruit")
			);
	}

	@Test
	public void insertingCorrect() {
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
			.isGreaterThan(1L);
	}
}

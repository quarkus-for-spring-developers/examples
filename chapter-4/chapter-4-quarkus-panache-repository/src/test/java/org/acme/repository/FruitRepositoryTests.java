package org.acme.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
@TestTransaction
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void startingPointAsExpected() {
		List<Fruit> fruits = this.fruitRepository.listAll();

		assertThat(fruits)
			.hasSize(2)
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactlyInAnyOrder(
				tuple("Apple", "Hearty fruit"),
				tuple("Pear", "Juicy fruit")
			);
	}

	@Test
	public void insertingCorrect() {
		this.fruitRepository.persist(new Fruit(null, "Grapefruit", "Summer fruit"));

		Optional<Fruit> fruit = this.fruitRepository.findByName("Grapefruit");
		assertThat(fruit)
			.isNotNull()
			.isPresent()
			.get()
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.get().getId())
			.isNotNull()
			.isGreaterThan(1L);
	}
}

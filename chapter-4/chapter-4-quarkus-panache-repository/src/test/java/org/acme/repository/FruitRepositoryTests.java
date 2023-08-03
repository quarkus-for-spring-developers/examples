package org.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import jakarta.inject.Inject;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestTransaction
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void findByName() {
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
			.isGreaterThan(2L);
	}
}

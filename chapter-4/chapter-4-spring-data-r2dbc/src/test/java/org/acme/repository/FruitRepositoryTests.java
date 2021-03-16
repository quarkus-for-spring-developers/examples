package org.acme.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.acme.TestContainerBase;
import org.acme.TestTransaction;
import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

class FruitRepositoryTests extends TestContainerBase {
	@Autowired
	FruitRepository fruitRepository;

	@Autowired
	TestTransaction testTransaction;

	@Test
	public void startingPointAsExpected() {
		List<Fruit> fruits = this.fruitRepository.findAll()
			.collectList()
			.block();

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
		Optional<Fruit> fruit = this.fruitRepository
			.save(new Fruit(null, "Grapefruit", "Summer fruit"))
			.then(this.fruitRepository.findByName("Grapefruit"))
			.as(this.testTransaction::withRollback)
			.blockOptional();

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

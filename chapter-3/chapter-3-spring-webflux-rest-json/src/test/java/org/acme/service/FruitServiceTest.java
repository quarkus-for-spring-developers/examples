package org.acme.service;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class FruitServiceTest {
	FruitService fruitService = new FruitService();

	@Test
	public void getFruits() {
		StepVerifier.create(this.fruitService.getFruits())
			.expectNextCount(2)
			.verifyComplete();
	}

	@Test
	public void getFruitFound() {
		StepVerifier.create(this.fruitService.getFruit("Apple"))
			.expectNextMatches(fruit -> "Apple".equalsIgnoreCase(fruit.getName()) && "Winter fruit".equalsIgnoreCase(fruit.getDescription()))
			.verifyComplete();
	}

	@Test
	public void getFruitNotFound() {
		StepVerifier.create(this.fruitService.getFruit("Pear"))
			.verifyComplete();
	}

	@Test
	public void addFruit() {
		StepVerifier.create(this.fruitService.addFruit(new Fruit("Pear", "Delicious fruit")))
			.expectNextCount(3)
			.verifyComplete();
	}

	@Test
	public void delete() {
		StepVerifier.create(this.fruitService.deleteFruit("Apple"))
			.verifyComplete();

		StepVerifier.create(this.fruitService.getFruits())
			.expectNextCount(1)
			.verifyComplete();
	}
}

package org.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
	FruitService fruitService = new FruitService();

	@Test
	public void getFruits() {
		assertThat(this.fruitService.getFruits())
			.hasSize(2);
	}

	@Test
	public void getFruitFound() {
		assertThat(this.fruitService.getFruit("Apple"))
			.isPresent()
			.get()
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactly("Apple", "Winter fruit");
	}

	@Test
	public void getFruitNotFound() {
		assertThat(this.fruitService.getFruit("Pear"))
			.isNotPresent();
	}

	@Test
	public void addFruit() {
		assertThat(this.fruitService.addFruit(new Fruit("Pear", "Delicious fruit")))
			.hasSize(3);
	}

	@Test
	public void delete() {
		this.fruitService.deleteFruit("Apple");

		assertThat(this.fruitService.getFruits())
			.hasSize(1);
	}
}

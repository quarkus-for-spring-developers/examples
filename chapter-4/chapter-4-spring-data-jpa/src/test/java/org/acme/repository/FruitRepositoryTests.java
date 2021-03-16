package org.acme.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class FruitRepositoryTests {
	@Autowired
	FruitRepository fruitRepository;

	@Test
	public void startingPointAsExpected() {
		List<Fruit> fruits = this.fruitRepository.findAll();

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
		this.fruitRepository.save(new Fruit(null, "Grapefruit", "Summer fruit"));

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

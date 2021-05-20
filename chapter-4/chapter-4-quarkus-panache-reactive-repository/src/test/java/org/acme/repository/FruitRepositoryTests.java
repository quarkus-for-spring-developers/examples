package org.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import javax.inject.Inject;

import org.acme.TestTransaction;
import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void findByName() {
		Fruit fruit = TestTransaction.withRollback(() ->
			this.fruitRepository
				.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
				.replaceWith(this.fruitRepository.findByName("Grapefruit"))
		)
			.await()
			.atMost(Duration.ofSeconds(10));

		assertThat(fruit)
			.isNotNull()
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.getId())
			.isNotNull()
			.isGreaterThan(2L);
	}
}

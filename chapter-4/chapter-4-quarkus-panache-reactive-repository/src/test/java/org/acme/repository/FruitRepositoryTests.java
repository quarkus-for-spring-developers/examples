package org.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.acme.TestTransaction;
import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

@QuarkusTest
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void findByName() {
		Fruit fruit = this.fruitRepository
			.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
			.replaceWith(this.fruitRepository.findByName("Grapefruit"))
			.stage(TestTransaction::withRollback)
			.subscribe()
			.withSubscriber(UniAssertSubscriber.create())
			.await()
			.assertCompleted()
			.getItem();

		assertThat(fruit)
			.isNotNull()
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.getId())
			.isNotNull()
			.isGreaterThan(2L);
	}
}

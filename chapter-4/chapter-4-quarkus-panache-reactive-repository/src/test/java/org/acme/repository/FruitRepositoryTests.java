package org.acme.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.inject.Inject;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void startingPointAsExpected() {
		List<Fruit> fruits = this.fruitRepository.listAll()
			.subscribe()
			.withSubscriber(UniAssertSubscriber.create())
			.await()
			.assertCompleted()
			.getItem();

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
		Fruit fruit = Panache.getSession().withTransaction(tx -> {
			tx.markForRollback();

			return this.fruitRepository.persistAndFlush(new Fruit(null, "Grapefruit", "Summer fruit"))
				.replaceWith(this.fruitRepository.findByName("Grapefruit"));
		})
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

package org.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.inject.Inject;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;

@QuarkusTest
@TestReactiveTransaction
class FruitRepositoryTests {
	@Inject
	FruitRepository fruitRepository;

	@Test
	public void findByName(UniAsserter asserter) {
		asserter.assertThat(() ->
				this.fruitRepository.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
					.replaceWith(this.fruitRepository.findByName("Grapefruit")),
			fruit -> {
				assertThat(fruit)
					.isNotNull()
					.extracting(Fruit::getName, Fruit::getDescription)
					.containsExactly("Grapefruit", "Summer fruit");

				assertThat(fruit.getId())
					.isNotNull()
					.isGreaterThan(2L);
			}
		);
	}
}
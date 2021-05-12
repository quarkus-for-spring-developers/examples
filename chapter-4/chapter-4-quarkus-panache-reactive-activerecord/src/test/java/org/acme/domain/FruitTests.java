package org.acme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.acme.rest.TestTransaction;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

@QuarkusTest
public class FruitTests {
	@Test
	public void findByName() {
		Fruit fruit = Fruit.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
			.replaceWith(Fruit.findByName("Grapefruit"))
			.stage(TestTransaction::withRollback)
			.subscribe()
			.withSubscriber(UniAssertSubscriber.create())
			.await()
			.assertCompleted()
			.getItem();

		assertThat(fruit)
			.isNotNull()
			.extracting("name", "description")
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.id)
			.isNotNull()
			.isGreaterThan(2L);
	}
}

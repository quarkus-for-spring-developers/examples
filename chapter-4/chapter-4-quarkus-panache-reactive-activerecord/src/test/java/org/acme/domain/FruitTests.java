package org.acme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;

@QuarkusTest
@TestReactiveTransaction
public class FruitTests {
	@Test
	public void findByName(UniAsserter asserter) {
		asserter.assertThat(() ->
				Fruit.persist(new Fruit(null, "Grapefruit", "Summer fruit"))
					.replaceWith(Fruit.findByName("Grapefruit")),
			fruit -> {
				assertThat(fruit)
					.isNotNull()
					.extracting("name", "description")
					.containsExactly("Grapefruit", "Summer fruit");

				assertThat(fruit.id)
					.isNotNull()
					.isGreaterThan(2L);
			}
		);
	}
}

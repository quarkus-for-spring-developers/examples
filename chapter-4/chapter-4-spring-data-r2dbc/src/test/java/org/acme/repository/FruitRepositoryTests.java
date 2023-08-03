package org.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.acme.ContainersConfig;
import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Import(ContainersConfig.class)
class FruitRepositoryTests {
	@Autowired
	FruitRepository fruitRepository;

	@Test
	@Rollback
	public void findByName() {
		var fruit = this.fruitRepository
			.save(new Fruit(null, "Grapefruit", "Summer fruit"))
			.then(this.fruitRepository.findByName("Grapefruit"))
			.block(Duration.ofSeconds(10));

		assertThat(fruit)
			.isNotNull()
			.extracting(Fruit::getName, Fruit::getDescription)
			.containsExactly("Grapefruit", "Summer fruit");

		assertThat(fruit.getId())
			.isNotNull()
			.isGreaterThan(2L);
	}
}

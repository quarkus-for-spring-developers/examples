package org.acme.rest;

import java.util.stream.Stream;

import org.acme.TestContainerBase;
import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class FruitControllerTests extends TestContainerBase {
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	FruitRepository fruitRepository;

	@Test
	public void getAll() {
		Mockito.when(this.fruitRepository.findAll())
			.thenReturn(Flux.fromStream(Stream.of(new Fruit(1L, "Apple", "Hearty Fruit"))));

		this.webTestClient.get()
			.uri("/fruits")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.size()").isEqualTo(1)
				.jsonPath("[0].id").isEqualTo(1)
				.jsonPath("[0].name").isEqualTo("Apple")
				.jsonPath("[0].description").isEqualTo("Hearty Fruit");

		Mockito.verify(this.fruitRepository).findAll();
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitFound() {
		Mockito.when(this.fruitRepository.findById(Mockito.eq(1L)))
			.thenReturn(Mono.just(new Fruit(1L, "Apple", "Hearty Fruit")));

		this.webTestClient.get()
			.uri("/fruits/1")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("id").isEqualTo(1)
			.jsonPath("name").isEqualTo("Apple")
			.jsonPath("description").isEqualTo("Hearty Fruit");

		Mockito.verify(this.fruitRepository).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitNotFound() {
		Mockito.when(this.fruitRepository.findById(Mockito.eq(1L)))
			.thenReturn(Mono.empty());

		this.webTestClient.get()
			.uri("/fruits/1")
			.exchange()
			.expectStatus().isNotFound();

		Mockito.verify(this.fruitRepository).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}
}

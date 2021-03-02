package org.acme.rest;

import java.util.List;

import org.acme.service.FruitService;
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
class FruitControllerTest {
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	FruitService fruitService;

	@Test
	public void list() {
		Mockito.when(this.fruitService.getFruits())
			.thenReturn(Flux.fromIterable(List.of(new Fruit("Apple", "Winter fruit"))));

		this.webTestClient.get()
			.uri("/fruits")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.size()").isEqualTo(1)
				.jsonPath("[0].name").isEqualTo("Apple")
				.jsonPath("[0].description").isEqualTo("Winter fruit");

		Mockito.verify(this.fruitService).getFruits();
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitFound() {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("Apple")))
			.thenReturn(Mono.just(new Fruit("Apple", "Winter fruit")));

		this.webTestClient.get()
			.uri("/fruits/Apple")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("name").isEqualTo("Apple")
				.jsonPath("description").isEqualTo("Winter fruit");

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitNotFound() {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("pear")))
			.thenReturn(Mono.empty());

		this.webTestClient.get()
			.uri("/fruits/pear")
			.exchange()
			.expectStatus().isNotFound()
			.expectBody().json("");

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("pear"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void add() {
		Mockito.when(this.fruitService.addFruit(Mockito.any(Fruit.class)))
			.thenReturn(Flux.fromIterable(List.of(new Fruit("Pear", "Refreshing fruit"))));

		this.webTestClient.post()
			.uri("/fruits")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{\"name\":\"Pear\",\"description\":\"Refreshing fruit\"}")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.size()").isEqualTo(1)
				.jsonPath("[0].name").isEqualTo("Pear")
				.jsonPath("[0].description").isEqualTo("Refreshing fruit");

		Mockito.verify(this.fruitService).addFruit(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void addInvalidFruit() {
		this.webTestClient.post()
			.uri("/fruits")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{\"description\":\"Description\"}")
			.exchange()
			.expectStatus().isBadRequest();

		Mockito.verifyNoInteractions(this.fruitService);
	}

	@Test
	public void deleteFruit() {
		this.webTestClient.delete()
			.uri("/fruits/Apple")
			.exchange()
			.expectStatus().isNoContent();

		Mockito.verify(this.fruitService).deleteFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}
}

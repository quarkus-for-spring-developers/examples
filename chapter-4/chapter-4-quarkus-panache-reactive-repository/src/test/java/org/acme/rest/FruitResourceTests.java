package org.acme.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.blankOrNullString;

import java.util.List;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;

@QuarkusTest
class FruitResourceTests {
	@InjectMock
	FruitRepository fruitRepository;

	@Test
	public void getAll() {
		Mockito.when(this.fruitRepository.listAll())
			.thenReturn(Uni.createFrom().item(List.of(new Fruit(1L, "Apple", "Hearty Fruit"))));

		given()
			.when().get("/fruits")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"$.size()", is(1),
					"[0].id", is(1),
					"[0].name", is("Apple"),
					"[0].description", is("Hearty Fruit")
				);

		Mockito.verify(this.fruitRepository).listAll();
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitFound() {
		Mockito.when(this.fruitRepository.findByName(Mockito.eq("Apple")))
			.thenReturn(Uni.createFrom().item(new Fruit(1L, "Apple", "Hearty Fruit")));

		given()
			.when().get("/fruits/Apple")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Apple"),
					"description", is("Hearty Fruit")
				);

		Mockito.verify(this.fruitRepository).findByName(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitNotFound() {
		Mockito.when(this.fruitRepository.findByName(Mockito.eq("Apple")))
			.thenReturn(Uni.createFrom().nullItem());

		given()
			.when().get("/fruits/Apple")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		Mockito.verify(this.fruitRepository).findByName(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void addFruit() {
		Mockito.when(this.fruitRepository.persist(Mockito.any(Fruit.class)))
			.thenReturn(Uni.createFrom().item(new Fruit(1L, "Grapefruit", "Summer fruit")));

		given()
			.when()
				.contentType(ContentType.JSON)
				.body("{\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
				.post("/fruits")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Grapefruit"),
					"description", is("Summer fruit")
				);

		Mockito.verify(this.fruitRepository).persist(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}
}

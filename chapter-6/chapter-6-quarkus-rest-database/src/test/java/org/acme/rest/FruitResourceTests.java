package org.acme.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

@QuarkusTest
class FruitResourceTests {
	@InjectMock
	FruitRepository fruitRepository;

	@Test
	public void getFruitFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.of(new Fruit(1L, "Apple", "Hearty Fruit")));

		given()
			.when().get("/api/fruits/1")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Apple"),
					"description", is("Hearty Fruit")
				);

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getFruitNotFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.empty());

		given()
			.when().get("/api/fruits/1")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void getAll() {
		Mockito.when(this.fruitRepository.listAll())
			.thenReturn(List.of(new Fruit(1L, "Apple", "Hearty Fruit")));

		given()
			.when().get("/api/fruits")
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
	public void addFruit() {
		Mockito.doNothing()
			.when(this.fruitRepository).persist(Mockito.any(Fruit.class));

		given()
			.when()
				.contentType(ContentType.JSON)
				.body("{\"id\":1,\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
				.post("/api/fruits")
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

	@Test
	public void putFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.of(new Fruit(1L, "Apple", "Hearty Fruit")));
		Mockito.doNothing()
			.when(this.fruitRepository).persist(Mockito.any(Fruit.class));

		given()
			.when()
				.contentType(ContentType.JSON)
				.body("{\"id\":1,\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
				.put("/api/fruits/1")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Grapefruit"),
					"description", is("Summer fruit")
				);

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verify(this.fruitRepository).persist(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void putNotFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.empty());

		given()
			.when()
				.contentType(ContentType.JSON)
				.body("{\"id\":1,\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
				.put("/api/fruits/1")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void deleteFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.of(new Fruit(1L, "Apple", "Hearty Fruit")));
		Mockito.doNothing()
			.when(this.fruitRepository).delete(Mockito.any(Fruit.class));

		given()
			.when().delete("/api/fruits/1")
			.then()
				.statusCode(204)
				.body(blankOrNullString());

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verify(this.fruitRepository).delete(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}

	@Test
	public void deleteNotFound() {
		Mockito.when(this.fruitRepository.findByIdOptional(Mockito.eq(1L)))
			.thenReturn(Optional.empty());

		given()
			.when().delete("/api/fruits/1")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		Mockito.verify(this.fruitRepository).findByIdOptional(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(this.fruitRepository);
	}
}

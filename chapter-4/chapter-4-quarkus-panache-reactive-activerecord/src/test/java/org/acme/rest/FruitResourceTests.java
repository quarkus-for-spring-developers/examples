package org.acme.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.blankOrNullString;

import java.util.List;

import org.acme.domain.Fruit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;

@QuarkusTest
class FruitResourceTests {
	@Test
	public void getAll() {
		PanacheMock.mock(Fruit.class);
		Mockito.when(Fruit.listAll())
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

		PanacheMock.verify(Fruit.class).listAll();
		PanacheMock.verifyNoMoreInteractions(Fruit.class);
	}

	@Test
	public void getFruitFound() {
		PanacheMock.mock(Fruit.class);
		Mockito.when(Fruit.findById(Mockito.eq(1L)))
			.thenReturn(Uni.createFrom().item(new Fruit(1L, "Apple", "Hearty Fruit")));

		given()
			.when().get("/fruits/1")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Apple"),
					"description", is("Hearty Fruit")
				);

		PanacheMock.verify(Fruit.class).findById(Mockito.eq(1L));
		PanacheMock.verifyNoMoreInteractions(Fruit.class);
	}

	@Test
	public void getFruitNotFound() {
		PanacheMock.mock(Fruit.class);
		Mockito.when(Fruit.findById(Mockito.eq(1L)))
			.thenReturn(Uni.createFrom().nullItem());

		given()
			.when().get("/fruits/1")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		PanacheMock.verify(Fruit.class).findById(Mockito.eq(1L));
		PanacheMock.verifyNoMoreInteractions(Fruit.class);
	}

	@Test
	public void addFruit() {
		PanacheMock.mock(Fruit.class);
		Mockito.when(Fruit.persist(Mockito.any(Fruit.class)))
			.thenReturn(Uni.createFrom().voidItem());

		given()
			.when()
				.contentType(ContentType.JSON)
				.body("{\"id\":1,\"name\":\"Grapefruit\",\"description\":\"Summer fruit\"}")
				.post("/fruits")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"id", is(1),
					"name", is("Grapefruit"),
					"description", is("Summer fruit")
				);

		PanacheMock.verify(Fruit.class).persist();
		PanacheMock.verifyNoMoreInteractions(Fruit.class);
	}
}

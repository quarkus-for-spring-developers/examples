package org.acme.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.blankOrNullString;

import java.util.List;

import org.acme.service.FruitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;

@QuarkusTest
public class FruitResourceTest {
	@InjectMock
	FruitService fruitService;

	@Test
	public void list() {
		Mockito.when(this.fruitService.getFruits())
			.thenReturn(List.of(new Fruit("Apple", "Winter fruit")));

		given()
			.when().get("/fruits")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"$.size()", is(1),
					"[0].name", is("Apple"),
					"[0].description", is("Winter fruit")
				);

		Mockito.verify(this.fruitService).getFruits();
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitFound() {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("Apple")))
			.thenReturn(Uni.createFrom().item(new Fruit("Apple", "Winter fruit")));

		given()
			.when().get("/fruits/Apple")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"name", is("Apple"),
					"description", is("Winter fruit")
				);

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void getFruitNotFound() {
		Mockito.when(this.fruitService.getFruit(Mockito.eq("pear")))
			.thenReturn(Uni.createFrom().nullItem());

		given()
			.when().get("/fruits/pear")
			.then()
				.statusCode(404)
				.body(blankOrNullString());

		Mockito.verify(this.fruitService).getFruit(Mockito.eq("pear"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void add() {
		Mockito.when(this.fruitService.addFruit(Mockito.any(Fruit.class)))
			.thenReturn(List.of(new Fruit("Pear", "Refreshing fruit")));

		given()
			.contentType(ContentType.JSON)
			.body(new Fruit("Pear", "Refreshing fruit"))
			.when().post("/fruits")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body(
					"$.size()", is(1),
					"[0].name", is("Pear"),
					"[0].description", is("Refreshing fruit")
				);

		Mockito.verify(this.fruitService).addFruit(Mockito.any(Fruit.class));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}

	@Test
	public void addInvalidFruit() {
		given()
			.contentType(ContentType.JSON)
			.body(new Fruit(null, "Description"))
			.when().post("/fruits")
			.then()
				.statusCode(400);

		Mockito.verifyNoInteractions(this.fruitService);
	}

	@Test
	public void deleteFruit() {
		given()
			.when().delete("/fruits/Apple")
			.then().statusCode(204);

		Mockito.verify(this.fruitService).deleteFruit(Mockito.eq("Apple"));
		Mockito.verifyNoMoreInteractions(this.fruitService);
	}
}

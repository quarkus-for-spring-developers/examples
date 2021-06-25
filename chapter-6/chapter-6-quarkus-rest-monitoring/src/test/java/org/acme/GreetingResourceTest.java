package org.acme;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class GreetingResourceTest {
	@Test
	public void sayHello() {
		when()
			.get("/hello/hh")
			.then()
				.statusCode(200)
				.contentType(ContentType.TEXT)
				.body(is("Hello!"));

		when()
			.get("/q/metrics")
			.then()
				.statusCode(200)
				.body(containsString("greeting_counter_total{name=\"hh\",}"));
	}
}

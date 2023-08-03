package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
public class GreetingResourceIT extends GreetingResourceTest {
	@Test
	public void testHelloEndpointNative() {
		given()
			.when().get("/hello-resteasy")
			.then()
				.statusCode(200)
				.body(is("Hello Quarkus for Spring Developer (prod)"));
	}
}

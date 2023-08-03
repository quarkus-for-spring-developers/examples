package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {
	@InjectMock
	GreetingService greetingService;

	@Test
	@DisabledOnIntegrationTest
	public void testHelloEndpoint() {
		Mockito.when(this.greetingService.getGreeting()).thenReturn("Hello Quarkus");

		given()
			.when().get("/hello-resteasy")
			.then()
				.statusCode(200)
				.body(is("Hello Quarkus"));

		Mockito.verify(this.greetingService).getGreeting();
		Mockito.verifyNoMoreInteractions(this.greetingService);
	}
}

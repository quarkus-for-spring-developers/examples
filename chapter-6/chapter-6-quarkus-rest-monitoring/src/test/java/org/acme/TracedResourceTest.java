package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TracedResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello/quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello!"));
    }
}
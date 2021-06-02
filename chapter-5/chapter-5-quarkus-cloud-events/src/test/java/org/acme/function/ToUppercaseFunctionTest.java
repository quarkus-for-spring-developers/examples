package org.acme.function;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ToUppercaseFunctionTest {

    @Test
    public void testUppercase() {
        given().contentType("application/json")
                .body("{\"message\": \"hello\"}")
                .header("ce-id", "42")
                .header("ce-specversion", "1.0")
                .post("/")
                .then().statusCode(200)
                .header("ce-id", notNullValue())
                .header("ce-specversion", equalTo("1.0"))
                .header("ce-source", equalTo("uppercase"))
                .header("ce-type", equalTo("uppercase.output"))
                .body("message", equalTo("HELLO"));
    }

}

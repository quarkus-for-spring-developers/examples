package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TracedResourceTest {
    @InjectMock
    FrancophoneService francophoneService;

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/traced/hello")
                .then()
                .statusCode(200)
                .body(is("hello"));
    }

    @Test
    public void testChain() {
        Mockito.when(this.francophoneService.bonjour())
          .thenReturn("bonjour");

        given()
          .when().get("/traced/chain")
          .then()
          .statusCode(200)
          .body(is("chain -> bonjour -> Hello!"));

        Mockito.verify(this.francophoneService).bonjour();
        Mockito.verifyNoMoreInteractions(this.francophoneService);
    }
}

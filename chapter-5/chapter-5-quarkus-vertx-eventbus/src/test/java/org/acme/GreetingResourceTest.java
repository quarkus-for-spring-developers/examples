package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class GreetingResourceTest {

    @InjectMock
    GreetingService greetingService;

    @Test
    public void testGreeting() {

        Mockito.when(this.greetingService.consume(Mockito.eq("events")))
            .thenReturn("EVENTS");
            
        given()
            .when().get("/async/events")
            .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is("EVENTS"));

        Mockito.verify(this.greetingService).consume(Mockito.eq("events"));
        Mockito.verifyNoMoreInteractions(this.greetingService);   

    }

    @Test
    public void testBlockingConsumer() {

        Mockito.when(this.greetingService.consumeBlocking(Mockito.eq("events")))
            .thenReturn("Processing Blocking I/O: events");

        given()
            .when().get("/async/block/events")
            .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is("Processing Blocking I/O: events"));

        Mockito.verify(this.greetingService).consumeBlocking(Mockito.eq("events"));
        Mockito.verifyNoMoreInteractions(this.greetingService);

    }

}
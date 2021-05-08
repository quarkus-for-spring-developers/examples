package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class GreetingResourceTest {

    @Inject
    GreetingResource greetingResource;

    @Test
    public void testGreeting() {
                
        String message = this.greetingResource.greeting("quarkus events")
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .await()
        .assertCompleted()
        .getItem();

        assertThat(message)
            .isNotNull()
            .isEqualTo("QUARKUS EVENTS");      

    }

    @Test
    public void testBlockingConsumer() {
                
        String message = this.greetingResource.blockingConsumer("blocking events")
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .await()
        .assertCompleted()
        .getItem();

        assertThat(message)
            .isNotNull()
            .isEqualTo("Processing Blocking I/O: blocking events");      
    }

}
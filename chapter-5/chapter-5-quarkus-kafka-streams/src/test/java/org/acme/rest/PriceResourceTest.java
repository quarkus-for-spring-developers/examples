package org.acme.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.client.impl.MultiInvoker;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;

@QuarkusTest
class PriceResourceTest {
    @TestHTTPEndpoint(PriceResource.class)
    @TestHTTPResource("/stream")
    URI uri;

    @Test
    public void sseEventStream() {
        List<Double> received = ClientBuilder.newClient()
          .target(this.uri)
          .request(MediaType.SERVER_SENT_EVENTS)
          .rx(MultiInvoker.class)
          .get(Double.class)
          .select().first(3)
          .subscribe().withSubscriber(AssertSubscriber.create(3))
          .assertSubscribed()
          .awaitItems(3, Duration.ofSeconds(20))
          .assertCompleted()
          .getItems();

        assertThat(received)
          .hasSize(3)
          .allMatch(value -> (value >= 0) && (value < 100));
    }
}

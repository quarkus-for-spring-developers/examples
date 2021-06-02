package org.acme.rest;

import static org.awaitility.Awaitility.await;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

import org.acme.DockerComposeResource;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(DockerComposeResource.class)
class PriceResourceTest {
    @TestHTTPEndpoint(PriceResource.class)
    @TestHTTPResource("/stream")
    URI uri;

    @Test
    void testPricesEventStream() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(this.uri);

        List<Double> received = new CopyOnWriteArrayList<>();

        SseEventSource source = SseEventSource.target(target).build();
        source.register(inboundSseEvent -> received.add(Double.valueOf(inboundSseEvent.readData())));
        source.open();
        await().atMost(Duration.ofSeconds(20)).until(() -> received.size() == 3);
        source.close();
    }
}

package org.acme.rest;

import java.time.Duration;

import org.acme.DockerComposeBase;
import org.acme.service.InMemoryChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@SpringBootTest
@AutoConfigureWebTestClient
class PriceControllerTests extends DockerComposeBase {
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	InMemoryChannel<Double> inMemoryChannel;

	@Test
	public void stream() {
		Mockito.when(this.inMemoryChannel.getPublisher())
			.thenReturn(Flux.just(1.1, 2.2).delayElements(Duration.ofSeconds(1)));

		this.webTestClient.get()
			.uri("/prices/stream")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
			.expectBody(String.class).isEqualTo("data:1.1\n\ndata:2.2\n\n");

		Mockito.verify(this.inMemoryChannel).getPublisher();
		Mockito.verifyNoMoreInteractions(this.inMemoryChannel);
	}
}

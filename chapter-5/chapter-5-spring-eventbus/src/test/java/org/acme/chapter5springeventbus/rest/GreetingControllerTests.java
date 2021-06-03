package org.acme.chapter5springeventbus.rest;

import java.time.Duration;

import org.acme.chapter5springeventbus.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class GreetingControllerTests {
	@Autowired
	WebTestClient webTestClient;

	@SpyBean
	GreetingService greetingService;

	@Test
	public void async() {
		this.webTestClient
			.get()
			.uri("/async/hi")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
			.expectBody(String.class).isEqualTo("HI");

		Mockito.verify(this.greetingService).consume(Mockito.argThat(mono ->"hi".equals(mono.block(Duration.ofSeconds(5)))));
		Mockito.verifyNoMoreInteractions(this.greetingService);
	}

	@Test
	public void blocking() {
		Mockito.when(this.greetingService.consumeBlocking(Mockito.eq("hi")))
			.thenReturn("HI");

		this.webTestClient
			.get()
			.uri("/async/block/hi")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
			.expectBody(String.class).isEqualTo("HI");

		Mockito.verify(this.greetingService).consumeBlocking(Mockito.eq("hi"));
		Mockito.verifyNoMoreInteractions(this.greetingService);
	}
}

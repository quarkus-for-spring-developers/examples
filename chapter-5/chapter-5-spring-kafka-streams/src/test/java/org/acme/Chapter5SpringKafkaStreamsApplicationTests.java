package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Chapter5SpringKafkaStreamsApplicationTests extends DockerComposeBase {
	@Autowired
	WebTestClient webTestClient;

	@Test
	void sseWorks() {
		List<Double> emittedPrices = this.webTestClient
			.get()
			.uri("/prices/stream")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
			.returnResult(Double.class)
			.getResponseBody()
			.take(3)
			.collectList()
			.block(Duration.ofSeconds(20));

		assertThat(emittedPrices)
			.isNotNull()
			.hasSize(3);

		emittedPrices.forEach(price ->
			assertThat(price)
				.isNotNull()
				.isGreaterThan(0)
		);
	}
}

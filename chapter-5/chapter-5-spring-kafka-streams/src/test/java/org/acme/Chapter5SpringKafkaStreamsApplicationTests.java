package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT4M")
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
			.block(Duration.ofMinutes(4));

		assertThat(emittedPrices)
			.isNotNull()
			.hasSize(3)
			.allMatch(value -> (value >= 0) && (value < 100));
	}
}

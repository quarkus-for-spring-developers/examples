package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT10M")
@Testcontainers
class Chapter5SpringKafkaStreamsApplicationTests {
	@Autowired
	WebTestClient webTestClient;

	@Container
	static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
//	static final DockerComposeContainer<?> DOCKER_COMPOSE =
//		new DockerComposeContainer<>(new File("docker-compose.yaml"))
//			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
//			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.cloud.stream.kafka.binder.brokers", () -> KAFKA.getBootstrapServers());
	}

//	private static String getKafkaBrokers() {
//		return String.format("%s:%s", DOCKER_COMPOSE.getServiceHost("kafka", 9092), DOCKER_COMPOSE.getServicePort("kafka", 9092));
//	}

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
			.block(Duration.ofMinutes(10));

		assertThat(emittedPrices)
			.isNotNull()
			.hasSize(3)
			.allMatch(value -> (value >= 0) && (value < 100));
	}
}

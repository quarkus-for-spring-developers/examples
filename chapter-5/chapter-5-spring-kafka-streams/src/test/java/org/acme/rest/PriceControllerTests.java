package org.acme.rest;

import java.time.Duration;

import org.acme.service.InMemoryChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
class PriceControllerTests {
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	InMemoryChannel<Double> inMemoryChannel;

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

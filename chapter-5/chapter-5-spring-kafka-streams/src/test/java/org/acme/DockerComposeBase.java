package org.acme;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * This class isn't used anymore due to some issues with the broker being shared between
 * test classes.
 * @see https://github.com/testcontainers/testcontainers-java/issues/5406
 */
@Testcontainers
@DirtiesContext
public abstract class DockerComposeBase {
	@Container
	static final KafkaContainer KAFKA =new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.cloud.stream.kafka.binder.brokers", KAFKA::getBootstrapServers);
	}
//	@Container
//	private static final DockerComposeContainer<?> DOCKER_COMPOSE =
//		new DockerComposeContainer<>(new File("docker-compose.yaml"))
//			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
//			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());
//
//	@DynamicPropertySource
//	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.cloud.stream.kafka.binder.brokers", DockerComposeBase::getKafkaBrokers);
//	}
//
//	private static String getKafkaBrokers() {
//		return String.format("%s:%s", DOCKER_COMPOSE.getServiceHost("kafka", 9092), DOCKER_COMPOSE.getServicePort("kafka", 9092));
//	}
}

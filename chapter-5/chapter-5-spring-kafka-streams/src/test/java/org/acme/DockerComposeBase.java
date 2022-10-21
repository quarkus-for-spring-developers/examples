package org.acme;

import java.io.File;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * This class isn't used anymore due to some issues with the broker being shared between
 * test classes.
 * @see https://github.com/testcontainers/testcontainers-java/issues/5406
 */
public abstract class DockerComposeBase {
	@Container
	private static final DockerComposeContainer<?> DOCKER_COMPOSE =
		new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.cloud.stream.kafka.binder.brokers", DockerComposeBase::getKafkaBrokers);
	}

	private static String getKafkaBrokers() {
		return String.format("%s:%s", DOCKER_COMPOSE.getServiceHost("kafka", 9092), DOCKER_COMPOSE.getServicePort("kafka", 9092));
	}
}

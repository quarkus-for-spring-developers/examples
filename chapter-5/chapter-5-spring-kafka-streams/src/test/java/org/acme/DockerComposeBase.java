package org.acme;

import java.io.File;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class DockerComposeBase {
	static final DockerComposeContainer<?> DOCKER_COMPOSE =
		new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", DockerComposeBase::getBootstrapServers);
	}

	static {
		DOCKER_COMPOSE.start();
	}

	protected static String getBootstrapServers() {
		return String.format(
			"%s:%s",
			DOCKER_COMPOSE.getServiceHost("kafka_1", 9092),
			DOCKER_COMPOSE.getServicePort("kafka_1", 9092)
		);
	}
}

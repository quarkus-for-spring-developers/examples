package org.acme;

import java.io.File;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public abstract class DockerComposeBase {
	private static final DockerComposeContainer<?> DOCKER_COMPOSE = new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

	static {
		DOCKER_COMPOSE.start();
		System.getProperties().setProperty("spring.cloud.stream.kafka.binder.brokers", getKafkaBrokers());
	}

	private static String getKafkaBrokers() {
		return String.format("%s:%s", DOCKER_COMPOSE.getServiceHost("kafka", 9092), DOCKER_COMPOSE.getServicePort("kafka", 9092));
	}
}

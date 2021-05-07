package org.acme;

import java.io.File;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class DockerComposeBase {
	static final DockerComposeContainer<?> DOCKER_COMPOSE =
		new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

	static {
		DOCKER_COMPOSE.start();
	}
}

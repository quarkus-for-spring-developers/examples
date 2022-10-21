package org.acme;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * This class isn't used anymore due to some issues with the broker being shared between
 * test classes.
 * @see https://github.com/testcontainers/testcontainers-java/issues/5406
 */
public abstract class DockerComposeBase {
	static final AtomicReference<DockerComposeContainer<?>> COMPOSE_CONTAINER_REFERENCE = new AtomicReference<>();

	@BeforeAll
	public static void beforeAll() {
		COMPOSE_CONTAINER_REFERENCE
			.updateAndGet(DockerComposeBase::createContainer)
			.start();
	}

	@AfterAll
	public static void afterAll() {
		COMPOSE_CONTAINER_REFERENCE
			.getAndSet(null)
			.stop();
	}

	private static DockerComposeContainer<?> createContainer(DockerComposeContainer<?> existing) {
		Optional.ofNullable(existing)
			.ifPresent(DockerComposeContainer::stop);

		return new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());
	}
}

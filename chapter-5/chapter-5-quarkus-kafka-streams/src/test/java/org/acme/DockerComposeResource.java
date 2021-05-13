package org.acme;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DockerComposeResource implements QuarkusTestResourceLifecycleManager {

    static final DockerComposeContainer<?> DOCKER_COMPOSE =
		new DockerComposeContainer<>(new File("docker-compose.yaml"))
			.withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
			.withExposedService("kafka", 1, 9092, Wait.forListeningPort());

    @Override
    public Map<String, String> start() {
        DOCKER_COMPOSE.start();
        return Collections.singletonMap("kafka.bootstrap.servers", "localhost:9092");
    }

    @Override
    public void stop() {
        DOCKER_COMPOSE.close();
    }

}
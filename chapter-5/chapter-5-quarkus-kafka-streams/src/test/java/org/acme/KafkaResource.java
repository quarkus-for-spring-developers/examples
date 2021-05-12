package org.acme;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class KafkaResource implements QuarkusTestResourceLifecycleManager {

    private final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Override
    public Map<String, String> start() {
        kafka.start();
        return Collections.singletonMap("kafka.bootstrap.servers", kafka.getBootstrapServers());
    }

    @Override
    public void stop() {
        kafka.close();
    }
}
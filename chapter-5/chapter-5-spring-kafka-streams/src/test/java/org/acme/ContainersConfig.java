package org.acme;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
public class ContainersConfig {
	@Container
	static ConfluentKafkaContainer KAFKA = new ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.1"));

	@DynamicPropertySource
	public static void kafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.cloud.stream.kafka.binder.brokers", KAFKA::getBootstrapServers);
	}
}
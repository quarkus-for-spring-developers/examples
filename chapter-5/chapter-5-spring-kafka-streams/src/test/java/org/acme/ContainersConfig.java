package org.acme;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {
	@Bean
	public KafkaContainer kafka(DynamicPropertyRegistry registry) {
		var kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.1"));
		registry.add("spring.cloud.stream.kafka.binder.brokers", kafka::getBootstrapServers);

		return kafka;
	}
}
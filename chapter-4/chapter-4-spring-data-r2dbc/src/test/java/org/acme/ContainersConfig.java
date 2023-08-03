package org.acme;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {
	@Bean
	@ServiceConnection
	public PostgreSQLContainer<?> postgres() {
		return new PostgreSQLContainer<>(
			DockerImageName.parse("quay.io/edeandrea/fruits-postgres:latest")
				.asCompatibleSubstituteFor("postgres")
		);
	}
}
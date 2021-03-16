package org.acme.repository;

import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {
	private static final PostgreSQLContainer<?> DB =
		new PostgreSQLContainer<>("postgres:13")
			.withDatabaseName("chapter4")
			.withUsername("chapter4")
			.withPassword("chapter4");

	@Override
	public Map<String, String> start() {
		DB.start();

		return Map.of(
			"quarkus.datasource.reactive.url",
			String.format("postgresql://%s:%d/%s", DB.getHost(), DB.getFirstMappedPort(), DB.getDatabaseName())
		);
	}

	@Override
	public void stop() {
		DB.stop();
	}
}

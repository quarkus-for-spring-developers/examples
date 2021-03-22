package org.acme;

import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {
	private static final PostgreSQLContainer<?> DB =
		new PostgreSQLContainer<>("postgres:13")
			.withDatabaseName("fruits")
			.withUsername("fruits")
			.withPassword("fruits")
			.withInitScript("db/schema.sql");

	@Override
	public Map<String, String> start() {
		DB.start();

		return Map.of(
			"quarkus.datasource.reactive.url",
			String.format("postgresql://%s:%d/%s", DB.getHost(), DB.getFirstMappedPort(), DB.getDatabaseName()),
			"quarkus.datasource.username", DB.getUsername(),
			"quarkus.datasource.password", DB.getPassword()
		);
	}

	@Override
	public void stop() {
		DB.stop();
	}
}

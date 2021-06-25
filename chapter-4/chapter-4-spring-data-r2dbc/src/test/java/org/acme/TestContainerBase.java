package org.acme;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@DirtiesContext
public abstract class TestContainerBase {
	@Container
	static final PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:13")
		.withDatabaseName("fruits")
		.withUsername("fruits")
		.withPassword("fruits")
		.withInitScript("db/schema.sql");

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", TestContainerBase::getDbUrl);
		registry.add("spring.r2dbc.username", DB::getUsername);
		registry.add("spring.r2dbc.password", DB::getPassword);
	}

	private static String getDbUrl() {
		return String.format("r2dbc:postgresql://%s:%d/%s", DB.getHost(), DB.getFirstMappedPort(), DB.getDatabaseName());
	}
}

package org.acme;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@Testcontainers
public abstract class TestContainerBase {
	@Container
	static final PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:13")
		.withDatabaseName("fruits")
		.withUsername("fruits")
		.withPassword("fruits")
		.withInitScript("db/schema.sql");

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url",
			() -> String.format("r2dbc:postgresql://%s:%d/%s", DB.getHost(), DB.getFirstMappedPort(), DB.getDatabaseName()));
		registry.add("spring.r2dbc.username",
			() -> DB.getUsername());
		registry.add("spring.r2dbc.password",
			() -> DB.getPassword());
	}
}

package org.acme;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Testcontainers
@SpringBootTest
public abstract class TestContainerBase {
	@Container
	static final PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:13")
		.withDatabaseName("chapter4")
		.withUsername("chapter4")
		.withPassword("chapter4");

	@DynamicPropertySource
	static void registerDynamicProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", () -> String.format("r2dbc:postgresql://%s:%d/chapter4", DB.getHost(), DB.getFirstMappedPort()));
		registry.add("spring.r2dbc.username", () -> DB.getUsername());
		registry.add("spring.r2dbc.password", () -> DB.getPassword());
		registry.add("spring.flyway.url", () -> String.format("jdbc:postgresql://%s:%d/chapter4", DB.getHost(), DB.getFirstMappedPort()));
		registry.add("spring.flyway.user", () -> DB.getUsername());
		registry.add("spring.flyway.password", () -> DB.getPassword());
	}
}

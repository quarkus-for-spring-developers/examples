package org.acme.config;

import org.flywaydb.core.Flyway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {
	@Bean(initMethod = "migrate")
	public Flyway flyway(Environment env) {
		return new Flyway(
			Flyway.configure()
				.dataSource(
					env.getRequiredProperty("spring.flyway.url"),
					env.getRequiredProperty("spring.flyway.user"),
					env.getRequiredProperty("spring.flyway.password")
				)
		);
	}
}

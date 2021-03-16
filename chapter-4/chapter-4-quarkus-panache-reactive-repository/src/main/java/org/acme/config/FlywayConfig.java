package org.acme.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class FlywayConfig {
	@ConfigProperty(name = "myapp.flyway.migrate-at-start")
	boolean runMigration;

	@ConfigProperty(name = "quarkus.datasource.reactive.url")
	String datasourceUrl;

	@ConfigProperty(name = "quarkus.datasource.username")
	String datasourceUsername;

	@ConfigProperty(name = "quarkus.datasource.password")
	String datasourcePassword;

	public void runMigration(@Observes StartupEvent startupEvent) {
		if (this.runMigration) {
			Flyway.configure()
				.dataSource("jdbc:" + this.datasourceUrl, this.datasourceUsername, this.datasourcePassword)
				.load()
				.migrate();
		}
	}
}

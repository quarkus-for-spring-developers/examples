package org.acme;

import java.util.Optional;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "greeting")
public interface GreetingProperties {
	String message();

	@WithDefault("!")
	String suffix();

	Optional<String> name();
}

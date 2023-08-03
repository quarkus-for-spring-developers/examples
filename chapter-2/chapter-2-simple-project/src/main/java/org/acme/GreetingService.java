package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GreetingService {
	private final String greeting;

	public GreetingService(@ConfigProperty(name = "greeting.name") String greeting) {
		this.greeting = greeting;
	}

	public String getGreeting() {
		return "Hello " + this.greeting;
	}
}

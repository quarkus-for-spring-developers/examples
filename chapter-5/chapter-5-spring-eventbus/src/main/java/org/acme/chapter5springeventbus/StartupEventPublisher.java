package org.acme.chapter5springeventbus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupEventPublisher implements CommandLineRunner {
	private final MySpringEventPublisher eventPublisher;

	public StartupEventPublisher(MySpringEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void run(String... args) {
		this.eventPublisher.publishCustomEvent("A test event message");
	}
}

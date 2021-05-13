package org.acme.chapter5springeventbus.service;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import reactor.core.publisher.Mono;

@MessageEndpoint
public class GreetingService {
	@ServiceActivator(inputChannel = "greeting", async = "true")
	public Mono<String> consume(Mono<String> name) {
		return name.map(String::toUpperCase);
	}

	@ServiceActivator(inputChannel = "blocking-greeting")
	public String consumeBlocking(String message) {
		return "Processing Blocking I/O: " + message;
	}
}

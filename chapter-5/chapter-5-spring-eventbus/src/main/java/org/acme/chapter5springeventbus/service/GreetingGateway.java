package org.acme.chapter5springeventbus.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import reactor.core.publisher.Mono;

@MessagingGateway
public interface GreetingGateway {
	@Gateway(requestChannel = "greeting")
	Mono<String> greeting(Mono<String> input);

	@Gateway(requestChannel = "blocking-greeting")
	String blockingGreeting(String input);
}

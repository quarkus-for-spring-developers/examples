package org.acme.chapter5springeventbus.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface GreetingGateway {
	@Gateway(requestChannel = "greeting")
	CompletableFuture<String> greeting(String input);

	@Gateway(requestChannel = "blocking-greeting")
	String blockingGreeting(String input);
}

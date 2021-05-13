package org.acme.chapter5springeventbus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class GreetingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GreetingService.class);

	@ServiceActivator(inputChannel = "greeting", async = "true")
	public String consume(String name) {
		String toUpperCase = name.toUpperCase();
		LOGGER.info("{}.consume({}) = {}", getClass().getName(), name, toUpperCase);
		return toUpperCase;
	}

	@ServiceActivator(inputChannel = "blocking-greeting")
	public String consumeBlocking(String message) {
		LOGGER.info("{}.consumeBlocking({})", getClass().getName(), message);
		return "Processing Blocking I/O: " + message;
	}
}

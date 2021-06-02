package org.acme.chapter5springeventbus.rest;

import org.acme.chapter5springeventbus.service.GreetingGateway;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/async")
public class GreetingController {
	private final GreetingGateway greetingGateway;

	public GreetingController(GreetingGateway greetingGateway) {
		this.greetingGateway = greetingGateway;
	}

	@GetMapping(path = "/{name}", produces = MediaType.TEXT_PLAIN_VALUE)
	public Mono<String> greeting(@PathVariable String name) {
		return this.greetingGateway.greeting(Mono.justOrEmpty(name));
	}

	@GetMapping(path = "/block/{message}", produces = MediaType.TEXT_PLAIN_VALUE)
	public Mono<String> blockingConsumer(@PathVariable String message) {
		return Mono.justOrEmpty(this.greetingGateway.blockingGreeting(message));
	}
}

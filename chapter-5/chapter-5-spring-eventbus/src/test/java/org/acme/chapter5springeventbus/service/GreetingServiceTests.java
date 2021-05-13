package org.acme.chapter5springeventbus.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GreetingServiceTests {
	@Test
	public void consume() {
		StepVerifier.create(new GreetingService().consume(Mono.just("hi")))
			.expectNext("HI")
			.verifyComplete();
	}

	@Test
	public void consumeBlocking() {
		assertThat(new GreetingService().consumeBlocking("hi"))
			.isEqualTo("Processing Blocking I/O: hi");
	}
}

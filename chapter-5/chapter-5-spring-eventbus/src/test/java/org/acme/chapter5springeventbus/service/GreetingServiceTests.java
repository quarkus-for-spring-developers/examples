package org.acme.chapter5springeventbus.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GreetingServiceTests {
	@Test
	public void consume() {
		assertThat(new GreetingService().consume("hi"))
			.isEqualTo("HI");
	}

	@Test
	public void consumeBlocking() {
		assertThat(new GreetingService().consumeBlocking("hi"))
			.isEqualTo("Processing Blocking I/O: hi");
	}
}

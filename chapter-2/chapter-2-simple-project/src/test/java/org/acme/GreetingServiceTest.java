package org.acme;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingServiceTest {
	@Test
	void getGreetingOk() {
		Assertions.assertEquals("Hello Quarkus", new GreetingService("Quarkus").getGreeting());
	}
}

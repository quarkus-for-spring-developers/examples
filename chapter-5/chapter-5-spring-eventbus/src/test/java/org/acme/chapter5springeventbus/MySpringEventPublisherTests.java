package org.acme.chapter5springeventbus;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class MySpringEventPublisherTests {
	@Autowired
	MySpringEventPublisher eventPublisher;

	@SpyBean
	MyListener listener;

	@Test
	public void messageReceived() {
		this.eventPublisher.publishCustomEvent("Test");

		Mockito.verify(this.listener).handleEvent(Mockito.eq(new MySpringEvent("Test")));
		Mockito.verifyNoMoreInteractions(this.listener);
	}
}

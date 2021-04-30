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

		// times(2) because the application will publish an event on startup
		Mockito.verify(this.listener, Mockito.times(2)).handleEvent(Mockito.any(MySpringEvent.class));
		Mockito.verifyNoMoreInteractions(this.listener);
	}
}

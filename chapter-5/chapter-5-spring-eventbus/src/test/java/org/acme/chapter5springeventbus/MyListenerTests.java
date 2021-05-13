package org.acme.chapter5springeventbus;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class MyListenerTests {
	@SpyBean
	MyListener listener;

	@Test
	public void messageReceived() {
		Mockito.verify(this.listener).handleEvent(Mockito.eq(new MySpringEvent("A test event message")));
		Mockito.verifyNoMoreInteractions(this.listener);
	}
}

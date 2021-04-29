package org.acme.chapter5springeventbus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class MySpringEventPublisherTests {
	@Autowired
	MySpringEventPublisher eventPublisher;

	@Autowired
	MyListener listener;

	@Test
	public void messageReceived() {
		List<MySpringEvent> events = (List<MySpringEvent>) ReflectionTestUtils.getField(this.listener, "events");

		assertThat(events)
			.isNotNull()
			.hasSize(1);

		this.eventPublisher.publishCustomEvent("Test");

		assertThat(events)
			.isNotNull()
			.hasSize(2);

		assertThat(events.get(1))
			.isNotNull()
			.isExactlyInstanceOf(MySpringEvent.class)
			.extracting(MySpringEvent::getMessage)
			.isEqualTo("Test");
	}
}

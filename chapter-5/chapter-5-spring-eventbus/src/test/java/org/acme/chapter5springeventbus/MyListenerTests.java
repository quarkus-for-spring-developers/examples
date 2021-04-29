package org.acme.chapter5springeventbus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class MyListenerTests {
	@Autowired
	MyListener listener;

	@Test
	public void messageReceived() {
		List<MySpringEvent> events = (List<MySpringEvent>) ReflectionTestUtils.getField(this.listener, "events");

		assertThat(events)
			.isNotNull()
			.hasSize(1);

		assertThat(events.get(0))
			.isNotNull()
			.isExactlyInstanceOf(MySpringEvent.class)
			.extracting(MySpringEvent::getMessage)
			.isEqualTo("A test event message");
	}
}

package org.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.acme.DockerComposeBase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
class KafkaProducerConsumerTests extends DockerComposeBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConsumerTests.class);

	@Autowired
	KafkaProducer kafkaProducer;

	@SpyBean
	KafkaConsumer kafkaConsumer;

	@Captor
	ArgumentCaptor<String> messageCaptor;

	@Test
	public void listenMessage() throws InterruptedException {
		this.kafkaProducer.sendData("Test Message");

		Mockito.verify(this.kafkaConsumer, Mockito.timeout(20 * 1000))
			.listenMessage(this.messageCaptor.capture());

		assertThat(this.messageCaptor.getValue())
			.isEqualTo("Test Message");
	}
}

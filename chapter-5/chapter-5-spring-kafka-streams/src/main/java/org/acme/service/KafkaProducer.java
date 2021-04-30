package org.acme.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final String topicName;

	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.name}") String topicName) {
		this.kafkaTemplate = kafkaTemplate;
		this.topicName = topicName;
	}

	public void sendData(String payload) {
		LOGGER.info("Sent message: Payload[{}] to Kafka Topic[{}]", payload, this.topicName);
		this.kafkaTemplate.send(this.topicName, payload);
	}
}

package org.acme.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class KafkaProducerController {
	private final KafkaProducer producer;

	public KafkaProducerController(KafkaProducer producer) {
		this.producer = producer;
	}

	@GetMapping("/{message}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void send(@PathVariable String message) {
		this.producer.sendData(message);
	}
}

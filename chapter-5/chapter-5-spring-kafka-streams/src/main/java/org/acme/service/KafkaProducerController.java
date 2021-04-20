package org.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class KafkaProducerController {

	@Autowired
	private KafkaProducer producer;
	
	@GetMapping
	@RequestMapping("/{message}")
	public void send(@PathVariable String message) throws Exception {
		producer.sendData(message);
	}
}

package org.acme.rest;

import org.acme.service.InMemoryChannel;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/prices")
public class PriceController {
	private final InMemoryChannel<Double> inMemoryChannel;

	public PriceController(InMemoryChannel<Double> inMemoryChannel) {
		this.inMemoryChannel = inMemoryChannel;
	}

	@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Double>> stream() {
		return Flux.from(this.inMemoryChannel.getPublisher())
			.map(price -> ServerSentEvent.builder(price).build());
	}
}

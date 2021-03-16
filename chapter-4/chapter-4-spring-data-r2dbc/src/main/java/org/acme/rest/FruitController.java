package org.acme.rest;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fruits")
public class FruitController {
	private final FruitRepository fruitRepository;

	public FruitController(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Fruit> getAll() {
		return this.fruitRepository.findAll();
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Fruit>> getFruit(@PathVariable Long id) {
		return this.fruitRepository.findById(id)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}

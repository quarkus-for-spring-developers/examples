package org.acme.rest;

import javax.validation.Valid;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Fruit>> getFruit(@PathVariable String name) {
		return this.fruitRepository.findByName(name)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Mono<Fruit> addFruit(@Valid @RequestBody Fruit fruit) {
		return this.fruitRepository.save(fruit);
	}
}

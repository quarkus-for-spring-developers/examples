package org.acme.rest;

import java.util.Collection;

import javax.validation.Valid;

import org.acme.service.FruitService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class FruitController {
	private final FruitService fruitService;

	public FruitController(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Fruit> list() {
		return this.fruitService.getFruits();
	}

	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fruit> getFruit(@PathVariable("name") String name) {
		return this.fruitService.getFruit(name)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Fruit> add(@Valid @RequestBody Fruit fruit) {
		return this.fruitService.addFruit(fruit);
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@Valid @RequestBody Fruit fruit) {
		this.fruitService.deleteFruit(fruit.getName());
	}
}

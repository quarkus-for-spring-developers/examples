package org.acme.rest;

import javax.validation.Valid;

import org.acme.domain.Fruit;
import org.acme.service.FruitService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.ServerSentEvent.Builder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fruits")
@Tag(name = "Fruit Resource", description = "Endpoints for fruits")
public class FruitController {
	private final FruitService fruitService;

	public FruitController(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all fruits", description = "Get all fruits")
	@ApiResponse(responseCode = "200", description = "All fruits")
	public Flux<Fruit> list() {
		return this.fruitService.getFruits();
	}

	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get a fruit", description = "Get a fruit")
	@ApiResponse(responseCode = "200", description = "Requested fruit")
	@ApiResponse(responseCode = "404", description = "Fruit not found")
	public Mono<ResponseEntity<Fruit>> getFruit(@Parameter(required = true, description = "Fruit name") @PathVariable String name) {
		return this.fruitService.getFruit(name)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Add a new fruit", description = "Add a new fruit")
	@ApiResponse(responseCode = "200", description = "Fruit added")
	public Flux<Fruit> add(@Parameter(required = true, description = "Fruit to add") @Valid @RequestBody Fruit fruit) {
		return this.fruitService.addFruit(fruit);
	}

	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a fruit", description = "Delete a fruit")
	@ApiResponse(responseCode = "204", description = "Fruit deleted")
	public Mono<Void> deleteFruit(@Parameter(required = true, description = "Fruit name") @PathVariable String name) {
		return this.fruitService.deleteFruit(name);
	}

	@GetMapping("/error")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Do something that will most likely generate an error", description = "Do something that will most likely generate an error")
	@ApiResponse(responseCode = "204", description = "Success")
	@ApiResponse(responseCode = "500", description = "Something bad happened")
	public Mono<Void> doSomethingGeneratingError() {
		return this.fruitService.performWorkGeneratingError();
	}

	@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@Operation(summary = "Stream a fruit every second", description = "Stream a fruit every second")
	@ApiResponse(responseCode = "200", description = "One fruit every second")
	public Flux<ServerSentEvent<Fruit>> streamFruits() {
		return this.fruitService.streamFruits()
			.map(ServerSentEvent::builder)
			.map(Builder::build);
	}
}

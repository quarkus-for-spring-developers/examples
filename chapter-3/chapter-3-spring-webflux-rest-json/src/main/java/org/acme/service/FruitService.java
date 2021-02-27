package org.acme.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.acme.rest.Fruit;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FruitService {
	private ConcurrentMap<String, Fruit> fruits = new ConcurrentHashMap<>();

	public FruitService() {
		this.fruits.put("Apple", new Fruit("Apple", "Winter fruit"));
		this.fruits.put("Pineapple", new Fruit("Pineapple", "Tropical fruit"));
	}

	public Flux<Fruit> getFruits() {
		return Flux.fromIterable(this.fruits.values());
	}

	public Mono<Fruit> getFruit(String fruitName) {
		return Mono.justOrEmpty(this.fruits.get(fruitName));
	}

	public Flux<Fruit> addFruit(Fruit fruit) {
		return Mono.fromSupplier(() -> this.fruits.put(fruit.getName(), fruit))
			.thenMany(Flux.fromIterable(this.fruits.values()));
	}

	public Mono<Void> deleteFruit(String fruitName) {
		return Mono.fromSupplier(() -> this.fruits.remove(fruitName))
			.then();
	}
}

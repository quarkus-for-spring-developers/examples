package org.acme.service;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.acme.domain.CustomRuntimeException;
import org.acme.domain.Fruit;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class FruitService {
	private final ConcurrentMap<String, Fruit> fruits = new ConcurrentHashMap<>();

	public FruitService() {
		this.fruits.put("Apple", new Fruit("Apple", "Winter fruit"));
		this.fruits.put("Pineapple", new Fruit("Pineapple", "Tropical fruit"));
	}

	public Collection<Fruit> getFruits() {
		return this.fruits.values();
	}

	public Uni<Fruit> getFruit(String fruitName) {
		return Uni.createFrom().item(this.fruits.get(fruitName));
	}

	public Collection<Fruit> addFruit(Fruit fruit) {
		this.fruits.put(fruit.getName(), fruit);
		return this.fruits.values();
	}

	public void deleteFruit(String fruitName) {
		this.fruits.remove(fruitName);
	}

	public void performWorkGeneratingError() {
		throw new CustomRuntimeException("Got some kind of error from somewhere");
	}

	public Multi<Fruit> streamFruits() {
		return Multi.createFrom()
			.ticks()
			.every(Duration.ofSeconds(1))
			.onItem().transform(tick ->
				this.fruits.values()
					.stream()
					.sorted(Comparator.comparing(Fruit::getName))
					.collect(Collectors.toList())
					.get(tick.intValue())
			)
			.select().first(this.fruits.size());
	}
}

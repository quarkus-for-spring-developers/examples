package org.acme.service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.acme.domain.CustomRuntimeException;
import org.acme.domain.Fruit;

import org.springframework.stereotype.Service;

@Service
public class FruitService {
	private ConcurrentMap<String, Fruit> fruits = new ConcurrentHashMap<>();

	public FruitService() {
		this.fruits.put("Apple", new Fruit("Apple", "Winter fruit"));
		this.fruits.put("Pineapple", new Fruit("Pineapple", "Tropical fruit"));
	}

	public Collection<Fruit> getFruits() {
		return this.fruits.values();
	}

	public Optional<Fruit> getFruit(String fruitName) {
		return Optional.ofNullable(this.fruits.get(fruitName));
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
}

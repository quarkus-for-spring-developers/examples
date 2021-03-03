package org.acme.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.acme.rest.Fruit;

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

	public void streamFruits(Consumer<Fruit> fruitConsumer, Runnable completionSignal) {
		AtomicInteger counter = new AtomicInteger();
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		executor.scheduleWithFixedDelay(() -> {
			int tick = counter.getAndIncrement();
			List<Fruit> fruits = this.fruits.values()
				.stream()
				.sorted(Comparator.comparing(Fruit::getName))
				.collect(Collectors.toList());

			if (tick < fruits.size()) {
				fruitConsumer.accept(fruits.get(tick));
			}
			else {
				completionSignal.run();
				executor.shutdown();
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}

package org.acme.repository;

import org.acme.domain.Fruit;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface FruitRepository extends ReactiveCrudRepository<Fruit, Long> {
	Mono<Fruit> findByName(String name);
}

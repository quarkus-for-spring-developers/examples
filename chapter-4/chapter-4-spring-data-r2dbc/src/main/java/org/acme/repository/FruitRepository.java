package org.acme.repository;

import org.acme.domain.Fruit;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Mono;

public interface FruitRepository extends ReactiveSortingRepository<Fruit, Long> {
	Mono<Fruit> findByName(String name);
}

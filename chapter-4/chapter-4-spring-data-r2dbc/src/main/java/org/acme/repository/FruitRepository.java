package org.acme.repository;

import org.acme.domain.Fruit;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface FruitRepository extends ReactiveSortingRepository<Fruit, Long> {
	Mono<Fruit> findByName(String name);
}

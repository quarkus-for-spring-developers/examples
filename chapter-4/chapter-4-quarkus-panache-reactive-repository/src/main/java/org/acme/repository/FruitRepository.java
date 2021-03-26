package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.domain.Fruit;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class FruitRepository implements PanacheRepository<Fruit> {
	public Uni<Fruit> findByName(String name) {
		return find("name", name).firstResult();
	}
}

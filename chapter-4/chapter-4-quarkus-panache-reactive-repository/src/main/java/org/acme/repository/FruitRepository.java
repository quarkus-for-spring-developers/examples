package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.domain.Fruit;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit, Long> {
	public Uni<Fruit> findByName(String name) {
		return find("name", name).firstResult();
	}
}

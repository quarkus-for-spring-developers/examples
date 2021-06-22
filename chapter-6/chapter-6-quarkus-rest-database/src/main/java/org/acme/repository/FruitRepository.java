package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.domain.Fruit;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit, Long> {
}

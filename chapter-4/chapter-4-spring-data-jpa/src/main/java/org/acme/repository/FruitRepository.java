package org.acme.repository;

import java.util.Optional;

import org.acme.domain.Fruit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
	Optional<Fruit> findByName(String name);
}

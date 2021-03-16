package org.acme.domain;

import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "fruits")
public class Fruit extends PanacheEntity {
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Name is mandatory")
	public String name;
	public String description;

	public Fruit() {
	}

	public Fruit(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public static Optional<Fruit> findByName(String name) {
		return find("name", name).singleResultOptional();
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Fruit.class.getSimpleName() + "[", "]")
			.add("id=" + this.id)
			.add("name='" + this.name + "'")
			.add("description='" + this.description + "'")
			.toString();
	}
}

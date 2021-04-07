package org.acme.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;

@Entity
@Table(name = "fruits")
public class Fruit extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

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

	public static Uni<Fruit> findByName(String name) {
		return find("name", name).firstResult();
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Fruit.class.getSimpleName() + "[", "]")
			.add("id=" + this.id)
			.add("name='" + this.name + "'")
			.add("description='" + this.description + "'")
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Fruit fruit = (Fruit) o;
		return this.id.equals(fruit.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}

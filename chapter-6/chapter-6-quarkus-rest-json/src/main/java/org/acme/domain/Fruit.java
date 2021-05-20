package org.acme.domain;

import javax.validation.constraints.NotBlank;

public class Fruit {
	private String name;
	private String description;

	public Fruit(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Fruit() {
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

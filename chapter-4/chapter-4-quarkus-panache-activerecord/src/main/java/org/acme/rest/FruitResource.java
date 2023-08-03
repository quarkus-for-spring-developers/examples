package org.acme.rest;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;

@Path("/fruits")
public class FruitResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fruit> getAll() {
		return Fruit.listAll();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFruit(@PathParam("name") String name) {
		return Fruit.findByName(name)
			.map(fruit -> Response.ok(fruit).build())
			.orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Fruit addFruit(@Valid Fruit fruit) {
		Fruit.persist(fruit);
		return fruit;
	}
}

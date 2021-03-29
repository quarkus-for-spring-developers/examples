package org.acme.rest;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/fruits")
public class FruitResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<List<Fruit>> getAll() {
		return Fruit.listAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> getFruit(@PathParam("id") Long id) {
		return Fruit.findById(id)
			.onItem().ifNotNull().transform(fruit -> Response.ok(fruit).build())
			.onItem().ifNull().continueWith(() -> Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Uni<Fruit> addFruit(@Valid Fruit fruit) {
		return Panache.withTransaction(() ->
			fruit.persist()
				.replaceWith(fruit)
		);
	}
}

package org.acme.rest;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;
import org.acme.service.FruitService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/fruits")
public class FruitResource {
	private final FruitService fruitService;

	public FruitResource(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Fruit> list() {
		return this.fruitService.getFruits();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> getFruit(@PathParam("name") String name) {
		return this.fruitService.getFruit(name)
			.onItem().ifNotNull().transform(fruit -> Response.ok(fruit).build())
			.onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Fruit> add(@NotNull @Valid Fruit fruit) {
		return this.fruitService.addFruit(fruit);
	}

	@DELETE
	@Path("/{name}")
	public void deleteFruit(@PathParam("name") String name) {
		this.fruitService.deleteFruit(name);
	}

	@GET
	@Path("/error")
	public void doSomethingGeneratingError() {
		this.fruitService.performWorkGeneratingError();
	}

	@GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public Multi<Fruit> streamFruits() {
		return this.fruitService.streamFruits();
	}
}

package org.acme.rest;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

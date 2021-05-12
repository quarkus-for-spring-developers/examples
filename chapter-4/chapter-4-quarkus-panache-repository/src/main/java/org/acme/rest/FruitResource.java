package org.acme.rest;

import java.util.List;

import javax.transaction.Transactional;
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
import org.acme.repository.FruitRepository;

import io.smallrye.common.annotation.Blocking;

@Path("/fruits")
@Blocking
public class FruitResource {
	private final FruitRepository fruitRepository;

	public FruitResource(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fruit> getAll() {
		return this.fruitRepository.listAll();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFruit(@PathParam("name") String name) {
		return this.fruitRepository.findByName(name)
			.map(fruit -> Response.ok(fruit).build())
			.orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Fruit addFruit(@Valid Fruit fruit) {
		this.fruitRepository.persist(fruit);
		return fruit;
	}
}

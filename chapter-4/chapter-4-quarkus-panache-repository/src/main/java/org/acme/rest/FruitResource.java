package org.acme.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;

import io.smallrye.common.annotation.Blocking;

@Path("/fruits")
public class FruitResource {
	private final FruitRepository fruitRepository;

	public FruitResource(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Fruit> getAll() {
		return this.fruitRepository.listAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public Response getFruit(@PathParam("id") Long id) {
		return this.fruitRepository.findByIdOptional(id)
			.map(Response::ok)
			.map(ResponseBuilder::build)
			.orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}
}

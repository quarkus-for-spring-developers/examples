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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;

import io.smallrye.common.annotation.Blocking;

@Path("/fruits")
public class FruitResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Fruit> getAll() {
		return Fruit.listAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public Response getFruit(@PathParam("id") Long id) {
		return Fruit.findByIdOptional(id)
			.map(Response::ok)
			.map(ResponseBuilder::build)
			.orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Blocking
	@Transactional
	public Fruit addFruit(@Valid Fruit fruit) {
		Fruit.persist(fruit);
		return fruit;
	}
}

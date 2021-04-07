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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/fruits")
@Tag(name = "Fruit Resource", description = "Endpoints for fruits")
public class FruitResource {
	private final FruitService fruitService;

	public FruitResource(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all fruits", description = "Get all fruits")
	@APIResponse(responseCode = "200", description = "All fruits")
	public Collection<Fruit> list() {
		return this.fruitService.getFruits();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get a fruit", description = "Get a fruit")
	@APIResponse(responseCode = "200", description = "Requested fruit", content = @Content(schema = @Schema(implementation = Fruit.class)))
	@APIResponse(responseCode = "404", description = "Fruit not found")
	public Uni<Response> getFruit(@Parameter(required = true, description = "Fruit name") @PathParam("name") String name) {
		return this.fruitService.getFruit(name)
			.onItem().ifNotNull().transform(fruit -> Response.ok(fruit).build())
			.onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Add a new fruit", description = "Add a new fruit")
	@APIResponse(responseCode = "200", description = "Fruit added")
	public Collection<Fruit> add(@Parameter(required = true, description = "Fruit to add") @NotNull @Valid Fruit fruit) {
		return this.fruitService.addFruit(fruit);
	}

	@DELETE
	@Path("/{name}")
	@Operation(summary = "Delete a fruit", description = "Delete a fruit")
	@APIResponse(responseCode = "204", description = "Fruit deleted")
	public void deleteFruit(@Parameter(required = true, description = "Fruit name") @PathParam("name") String name) {
		this.fruitService.deleteFruit(name);
	}

	@GET
	@Path("/error")
	@Operation(summary = "Do something that will most likely generate an error", description = "Do something that will most likely generate an error")
	@APIResponse(responseCode = "204", description = "Success")
	@APIResponse(responseCode = "500", description = "Something bad happened")
	public void doSomethingGeneratingError() {
		this.fruitService.performWorkGeneratingError();
	}

	@GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Operation(summary = "Stream a fruit every second", description = "Stream a fruit every second")
	@APIResponse(responseCode = "200", description = "One fruit every second")
	public Multi<Fruit> streamFruits() {
		return this.fruitService.streamFruits();
	}
}

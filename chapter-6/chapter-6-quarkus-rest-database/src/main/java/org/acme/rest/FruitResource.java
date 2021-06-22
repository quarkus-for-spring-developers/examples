package org.acme.rest;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.domain.Fruit;
import org.acme.repository.FruitRepository;

import io.smallrye.common.annotation.Blocking;

@Path("/api/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Blocking
public class FruitResource {
    private final FruitRepository repository;

    public FruitResource(FruitRepository repository) {
        this.repository = repository;
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        return this.repository.findByIdOptional(id)
          .map(fruit -> Response.ok(fruit).build())
          .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }

    @GET
    public List<Fruit> getAll() {
        return this.repository.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Fruit post(@NotNull @Valid Fruit fruit) {
        this.repository.persist(fruit);
        return fruit;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response put(@PathParam("id") Long id, @NotNull @Valid Fruit fruit) {
        return this.repository.findByIdOptional(id)
          .map(f -> {
              f.setId(id);
              f.setName(fruit.getName());
              f.setDescription(fruit.getDescription());
              this.repository.persist(f);
              return Response.ok(f).build();
          })
          .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return this.repository.findByIdOptional(id)
          .map(fruit -> {
              this.repository.delete(fruit);
              return Response.noContent().build();
            })
          .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    }
}

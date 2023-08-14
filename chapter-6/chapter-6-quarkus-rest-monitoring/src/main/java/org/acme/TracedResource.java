package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import org.jboss.resteasy.reactive.client.impl.UniInvoker;

import io.quarkus.logging.Log;

import io.micrometer.core.annotation.Counted;
import io.smallrye.mutiny.Uni;

@Path("/traced")
public class TracedResource {

    private final FrancophoneService exampleBean;

    public TracedResource(FrancophoneService exampleBean) {
        this.exampleBean = exampleBean;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    public String hello() {
        Log.info("hello");
        return "hello";
    }

    @GET
    @Path("/chain")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> chain(@Context UriInfo uriInfo) {
        return ClientBuilder.newClient()
          .target(uriInfo.getBaseUriBuilder().path("/hello/hh"))
          .request()
          .rx(UniInvoker.class)
          .get(String.class)
          .onItem().transform(response -> "chain -> " + this.exampleBean.bonjour() + " -> " + response);
    }
}

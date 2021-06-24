package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import io.micrometer.core.annotation.Counted;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.client.impl.UniInvoker;

import io.smallrye.mutiny.Uni;

@Path("/traced")
public class TracedResource {

    private static final Logger LOG = Logger.getLogger(TracedResource.class);
    private final FrancophoneService exampleBean;

    public TracedResource(FrancophoneService exampleBean) {
        this.exampleBean = exampleBean;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    public String hello() {
        LOG.info("hello");
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

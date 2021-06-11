package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import org.jboss.logging.Logger;

@Path("/")
public class TracedResource {

    private static final Logger LOG = Logger.getLogger(TracedResource.class);
    private final FrancophoneService exampleBean;

    public TracedResource(FrancophoneService exampleBean) {
        this.exampleBean = exampleBean;
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("hello");
        return "hello";
    }

    @GET
    @Path("/chain")
    @Produces(MediaType.TEXT_PLAIN)
    public String chain() {
        GreetingResource resourceClient = RestClientBuilder.newBuilder()
                .baseUri(uriInfo.getBaseUri())
                .build(GreetingResource.class);
        return "chain -> " + exampleBean.bonjour() + " -> " + resourceClient.sayHello("hh");
    }
}
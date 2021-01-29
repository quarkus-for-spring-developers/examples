package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello-resteasy")
public class GreetingResource {
	@ConfigProperty(name = "greeting.name")
	String greeting;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello " + this.greeting;
	}
}

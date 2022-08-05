package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @Inject
    private GreetingService greetingService;
    @Inject
    private ByeService byeService;

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") String name) {
        return greetingService.getGreeting(name);
    }

    @GET
    @Path("bye")
    @Produces(MediaType.TEXT_PLAIN)
    public String bye(@QueryParam("name") String name) {
        return byeService.getBye(name);
    }
}
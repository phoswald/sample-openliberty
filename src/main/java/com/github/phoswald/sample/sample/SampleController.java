package com.github.phoswald.sample.sample;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/pages")
public class SampleController {

    @GET
    @Path("/sample")
    @Produces(MediaType.TEXT_HTML)
    public Response getSamplePage() {
        return Response.ok(new SampleView().render(new SampleViewModel())).build();
    }
}

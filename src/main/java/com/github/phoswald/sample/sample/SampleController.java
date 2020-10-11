package com.github.phoswald.sample.sample;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
@Path("/pages/sample")
public class SampleController {

    @Inject
    @ConfigProperty(name = "app.sample.config")
    private String sampleConfig;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getSamplePage() {
        return Response.ok(new SampleView().render(new SampleViewModel(sampleConfig))).build();
    }
}

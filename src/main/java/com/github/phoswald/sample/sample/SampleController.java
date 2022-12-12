package com.github.phoswald.sample.sample;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

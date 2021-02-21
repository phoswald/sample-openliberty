package com.github.phoswald.sample.sample;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
@Controller
@Path("/pages/sample")
public class SampleController {

    @Inject
    @ConfigProperty(name = "app.sample.config")
    private String sampleConfig;
    
    @Inject
    private Models models;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getSamplePage() {
        models.put("model", new SampleViewModel(sampleConfig));
        return "sample.html";
    }
}

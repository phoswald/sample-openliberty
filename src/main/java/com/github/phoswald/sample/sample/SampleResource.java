package com.github.phoswald.sample.sample;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
@Path("/rest/sample")
public class SampleResource {

    @Inject
    @ConfigProperty(name = "app.sample.config")
    private String sampleConfig;

    @GET
    @Path("/time")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTime() {
        String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        return Response.ok(now).build();
    }

    @GET
    @Path("/config")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getConfig() {
        return Response.ok(sampleConfig).build();
    }

    @POST
    @Path("/echo-xml")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_XML)
    public Response postEchoXml(EchoRequest request) {
        EchoResponse response = new EchoResponse();
        response.setOuput("Received " + request.getInput());
        return Response.ok(response).build();
    }

    @POST
    @Path("/echo-json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postEchoJson(EchoRequest request) {
        EchoResponse response = new EchoResponse();
        response.setOuput("Received " + request.getInput());
        return Response.ok(response).build();
    }
}

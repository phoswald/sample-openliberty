package com.github.phoswald.sample.microprofile.sample;

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
@Path("/sample")
public class SampleResource {

    @Inject
    @ConfigProperty(name = "SampleConfigSettingA")
    private String settingA;

    @Inject
    @ConfigProperty(name = "SampleConfigSettingB")
    private String settingB;

    @GET
    @Path("/time")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTime() {
        String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        return Response.ok("The current time is " + now).build();
    }

    @GET
    @Path("/config")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getConfig() {
        String config = "" + //
                "SampleConfigSettingA = " + settingA + "\n" + //
                "SampleConfigSettingB = " + settingB + "\n"; //
        return Response.ok(config).build();
    }

    @POST
    @Path("/echo")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_XML)
    public Response postEcho(EchoRequest request) {
        EchoResponse response = new EchoResponse();
        response.setOuput("Received " + request.getInput());
        return Response.ok(response).build();
    }
}

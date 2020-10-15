package com.github.phoswald.sample.secured;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/pages/user")
@RolesAllowed("users")
public class UserController {

    @Context
    private HttpServletRequest request;

    @Inject
    private JsonWebToken jwtPrincipal;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getUserPage() {
    	UserViewModel viewModel = new UserViewModel();
    	if(request != null) {
    		if(request.getUserPrincipal() != null) {
        		viewModel.principalName = request.getUserPrincipal().getName();
    		} else {
    			viewModel.message = "request.getUserPrincipal() is null";
    		}
    	} else {
			viewModel.message = "request is null";
    	}
    	viewModel.jwtPrincipal = jwtPrincipal;
        return Response.ok(new UserView().render(viewModel)).build();
    }
}

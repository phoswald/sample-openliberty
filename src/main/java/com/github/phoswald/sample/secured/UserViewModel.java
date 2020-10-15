package com.github.phoswald.sample.secured;

import org.eclipse.microprofile.jwt.JsonWebToken;

public class UserViewModel {

    public String message;
    public String principalName;
    public JsonWebToken jwtPrincipal;
}

package org.bitionaire.elportero.resources;

import io.dropwizard.auth.Auth;
import org.bitionaire.elportero.auth.ApiClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/tokens")
public class TokensResource {

    @POST
    public Response login(@Auth final ApiClient apiClient) {

        return null;
    }

}

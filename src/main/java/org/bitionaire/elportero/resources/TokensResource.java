package org.bitionaire.elportero.resources;

import io.dropwizard.auth.Auth;
import org.bitionaire.elportero.auth.ApiClient;
import org.bitionaire.elportero.gateway.ApiGateway;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/tokens")
public class TokensResource {

    public ApiGateway apiGateway;

    public TokensResource(final ApiGateway apiGateway) {
        this.apiGateway = apiGateway;
    }

    @POST
    public Response login(@Auth final ApiClient apiClient) {
        return apiGateway.allowAccess(apiClient);
    }

}

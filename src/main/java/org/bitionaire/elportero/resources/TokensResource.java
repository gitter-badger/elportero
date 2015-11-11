package org.bitionaire.elportero.resources;

import io.dropwizard.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClient;
import org.bitionaire.elportero.gateway.ApiGateway;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/tokens")
public class TokensResource {

    public ApiGateway apiGateway;

    public TokensResource(final ApiGateway apiGateway) {
        this.apiGateway = apiGateway;
        log.info("tokens resource initialized with gateway class \"{}\"", apiGateway.getClass());
    }

    @POST
    public Response login(@Auth final ApiClient apiClient) {
        return apiGateway.allowAccess(apiClient);
    }

}

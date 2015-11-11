package org.bitionaire.elportero.resources;

import io.dropwizard.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClient;
import org.bitionaire.elportero.gateway.ApiGateway;
import org.bitionaire.elportero.gateway.ApiToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Slf4j
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON)
public class TokensResource {

    public ApiGateway apiGateway;

    public TokensResource(final ApiGateway apiGateway) {
        this.apiGateway = apiGateway;
        log.info("tokens resource initialized with gateway class \"{}\"", apiGateway.getClass());
    }

    @POST
    public Response login(@Auth final ApiClient apiClient) {
        final Optional<ApiToken> token = apiGateway.allowAccess(apiClient);
        if (token.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(token.get()).build();
        }
        log.error("could not create token for client {}", apiClient);
        return Response.serverError().build();
    }

}

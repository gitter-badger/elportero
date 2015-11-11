package org.bitionaire.elportero.gateway;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClient;
import org.bitionaire.elportero.gateway.kong.CreateConsumerRequest;
import org.bitionaire.elportero.gateway.kong.JwtPostResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KongApiGateway implements ApiGateway {

    private final Client httpClient;
    private final URI baseUrl;

    public KongApiGateway(final Client httpClient, final URL baseUrl) {
        this.httpClient = httpClient;
        try {
            this.baseUrl = baseUrl.toURI();
        } catch (final URISyntaxException e) {
            log.error("could not create URI from URL " + baseUrl, e);
            throw new RuntimeException("could not create URI", e);
        }
    }

    @Override
    public Response allowAccess(final ApiClient client) {
        if (clientExists(client) || added(client)) {
            final JwtPostResponse jwtPostResponse = httpClient
                    .target(baseUrl)
                    .path(String.format("/consumers/%s/jwt", client.getId()))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(null, JwtPostResponse.class);

            final JWTSigner jwtSigner = new JWTSigner(jwtPostResponse.getSecret());
            final Map<String, Object> claims = new HashMap<String, Object>() {{
                put("iss", jwtPostResponse.getKey());
            }};
            final String jwt = jwtSigner.sign(claims, new JWTSigner.Options().setAlgorithm(Algorithm.HS256));

            return Response.ok().header("Authorization", String.format("Bearer %s", jwt)).build();
        }
        log.error("client {} does not exist and could not be created", client);
        return Response.serverError().build();
    }

    private boolean clientExists(final ApiClient client) {
        final Response response = httpClient.target(baseUrl).path(String.format("/consumers/%s", client.getId())).request().get();
        return Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily();
    }

    private boolean added(final ApiClient client) {
        return Response.Status.CREATED.getStatusCode() == httpClient
                .target(baseUrl)
                .path(String.format("/consumers/%s", client.getId()))
                .request()
                .post(Entity.entity(new CreateConsumerRequest(client), MediaType.APPLICATION_JSON))
                .getStatus();
    }
}

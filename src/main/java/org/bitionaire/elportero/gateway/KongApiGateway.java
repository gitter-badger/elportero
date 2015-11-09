package org.bitionaire.elportero.gateway;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KongApiGateway implements ApiGateway {

    private final Client httpClient;
    private final URL baseUrl;

    public KongApiGateway(final Client httpClient, final URL baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public Response allowAccess(final ApiClient client) {
        try {
            final JwtPostResponse jwtPostResponse = httpClient
                    .target(baseUrl.toURI())
                    .path(String.format("/consumers/%s/jwt", client.getId())).request()
                    .post(null, JwtPostResponse.class);

            final JWTSigner jwtSigner = new JWTSigner(jwtPostResponse.getSecret());
            final Map<String, Object> claims = new HashMap<String, Object>() {{
                put("iss", jwtPostResponse.getKey());
            }};
            final String jwt = jwtSigner.sign(claims, new JWTSigner.Options().setAlgorithm(Algorithm.HS256));

            return Response.ok().header("Authorization", String.format("Bearer %s", jwt)).build();
        } catch (final URISyntaxException e) {
            log.error("could not create jwt for client " + client.getId(), e);
            return Response.serverError().build();
        }
    }
}

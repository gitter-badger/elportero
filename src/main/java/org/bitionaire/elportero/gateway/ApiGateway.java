package org.bitionaire.elportero.gateway;

import org.bitionaire.elportero.auth.ApiClient;

import javax.ws.rs.core.Response;
import java.util.Optional;

public interface ApiGateway {

    public Optional<ApiToken> allowAccess(final ApiClient client);

}

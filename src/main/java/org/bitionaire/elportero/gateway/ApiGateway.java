package org.bitionaire.elportero.gateway;

import org.bitionaire.elportero.auth.ApiClient;

import javax.ws.rs.core.Response;

public interface ApiGateway {

    public Response allowAccess(final ApiClient client);

}

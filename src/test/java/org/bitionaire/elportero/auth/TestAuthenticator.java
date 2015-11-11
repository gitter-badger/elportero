package org.bitionaire.elportero.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;

public class TestAuthenticator implements ApiClientAuthenticator<BasicCredentials, ApiClient> {

    @Override
    public AuthFilter getAuthFilter() {
        return new BasicCredentialAuthFilter.Builder<ApiClient>()
                .setAuthenticator(this)
                .setAuthorizer((principal, role) -> true)
                .setRealm(TestAuthenticator.class.getSimpleName())
                .buildAuthFilter();
    }

    @Override
    public Optional<ApiClient> authenticate(final BasicCredentials credentials) throws AuthenticationException {
        if ("bitionaire".equals(credentials.getUsername()) && "secret123".equals(credentials.getPassword())) {
            return Optional.of((ApiClient) () -> credentials.getUsername());
        }
        return Optional.absent();
    }

}

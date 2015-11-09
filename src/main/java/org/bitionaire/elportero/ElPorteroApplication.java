package org.bitionaire.elportero;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClientAuthenticator;
import org.bitionaire.elportero.auth.ApiClientAuthenticatorFactory;
import org.bitionaire.elportero.resources.TokensResource;

@Slf4j
public class ElPorteroApplication extends Application<ElPorteroConfiguration> {

    public static void main(final String... args) throws Exception {
        new ElPorteroApplication().run(args);
    }

    @Override
    public void run(final ElPorteroConfiguration configuration, final Environment environment) throws Exception {
        final ApiClientAuthenticatorFactory apiClientAuthenticatorFactory = configuration.getAuthenticator();
        final ApiClientAuthenticator authenticator = apiClientAuthenticatorFactory.initialize();

        environment.jersey().register(new AuthDynamicFeature(authenticator.getAuthFilter()));

        environment.jersey().register(new TokensResource());
    }
}

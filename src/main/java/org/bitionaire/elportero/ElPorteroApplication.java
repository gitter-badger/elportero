package org.bitionaire.elportero;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.elportero.auth.ApiClient;
import org.bitionaire.elportero.auth.ApiClientAuthenticator;
import org.bitionaire.elportero.auth.ApiClientAuthenticatorFactory;
import org.bitionaire.elportero.gateway.KongApiGateway;
import org.bitionaire.elportero.resources.TokensResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.client.Client;

@Slf4j
public class ElPorteroApplication extends Application<ElPorteroConfiguration> {

    public static void main(final String... args) throws Exception {
        new ElPorteroApplication().run(args);
    }

    @Override
    public void run(final ElPorteroConfiguration configuration, final Environment environment) throws Exception {
        final ApiClientAuthenticatorFactory apiClientAuthenticatorFactory = configuration.getAuthenticator();
        final ApiClientAuthenticator authenticator = apiClientAuthenticatorFactory.initialize();

        log.info("initialized authenticator with class \"{}\"", authenticator.getClass().getName());
        environment.jersey().register(new AuthDynamicFeature(authenticator.getAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(ApiClient.class));
        log.debug("auth feature added");

        final Client httpClient = new JerseyClientBuilder(environment).using(configuration.getHttpClient()).build("httpClient");
        environment.jersey().register(new TokensResource(new KongApiGateway(httpClient, configuration.getKongUrl())));
        log.info("all resources initialized");
    }
}

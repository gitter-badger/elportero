package org.bitionaire.elportero.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class BasicAuthenticator implements ApiClientAuthenticator<BasicCredentials, ApiClient> {

    private final Properties authProperties = new Properties();

    public BasicAuthenticator(final File authPropertiesFile) {
        try (final FileInputStream fileInputStream = new FileInputStream(authPropertiesFile)) {
            this.authProperties.load(fileInputStream);
        } catch (final IOException e) {
            log.error("failed to initialize auth properties", e);
        }
    }

    @Override
    public AuthFilter getAuthFilter() {
        return new BasicCredentialAuthFilter.Builder<ApiClient>()
                .setAuthenticator(this)
                .setPrefix(BasicAuthenticator.class.getSimpleName())
                .buildAuthFilter();
    }

    @Override
    public Optional<ApiClient> authenticate(final BasicCredentials credentials) throws AuthenticationException {
        if (authProperties.containsKey(credentials.getUsername()) && authProperties.getProperty(credentials.getUsername()).equals(credentials.getPassword())) {
            return Optional.of((ApiClient) () -> credentials.getUsername());
        }
        return Optional.absent();
    }
}

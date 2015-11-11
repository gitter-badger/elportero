package org.bitionaire.elportero.auth;

import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

public class BasicAuthenticatorTest {

    private static final File AUTH_PROPERTIES_FILE = new File(resourceFilePath("authentication.properties"));

    @Test
    public void testSuccessfulAuth() throws Exception {
        final BasicAuthenticator basicAuthenticator = new BasicAuthenticator(AUTH_PROPERTIES_FILE);
        assertTrue(basicAuthenticator.authenticate(new BasicCredentials("bitionaire", "secret123")).isPresent());
        assertEquals("bitionaire", basicAuthenticator.authenticate(new BasicCredentials("bitionaire", "secret123")).get().getId());
        assertFalse(basicAuthenticator.authenticate(new BasicCredentials("bitionaire", "wrong")).isPresent());
    }
}
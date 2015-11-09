package org.bitionaire.elportero.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.File;

public class BasicAuthenticatorFactory implements ApiClientAuthenticatorFactory<BasicAuthenticator> {

    @JsonProperty("properties")
    @Getter private File authProperties;

    @Override
    public BasicAuthenticator initialize() {
        return new BasicAuthenticator(authProperties);
    }
}

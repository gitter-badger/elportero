package org.bitionaire.elportero.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.File;

public class BasicAuthenticatorFactory extends ApiClientAuthenticatorFactory {

    @JsonProperty("properties")
    @Getter private File authProperties;

    @Override
    public Class getAuthenticatorClass() {
        return BasicAuthenticator.class;
    }
}

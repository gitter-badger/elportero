package org.bitionaire.elportero.auth;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "factory")
public abstract class ApiClientAuthenticatorFactory<A extends ApiClientAuthenticator> {

    public abstract Class<A> getAuthenticatorClass();

    public final ApiClientAuthenticator initialize() {
        try {
            final ApiClientAuthenticator authenticator = getAuthenticatorClass().newInstance();
            authenticator.initialize(this);
            return authenticator;
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("could not instantiate authenticator class", e);
        }
    }

}

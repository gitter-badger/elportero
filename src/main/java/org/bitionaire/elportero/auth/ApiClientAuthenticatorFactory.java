package org.bitionaire.elportero.auth;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "factory")
public interface ApiClientAuthenticatorFactory<A extends ApiClientAuthenticator> {

    public A initialize();

}

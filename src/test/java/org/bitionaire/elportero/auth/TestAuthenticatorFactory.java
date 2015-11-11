package org.bitionaire.elportero.auth;

public class TestAuthenticatorFactory implements ApiClientAuthenticatorFactory<TestAuthenticator> {
    @Override
    public TestAuthenticator initialize() {
        return new TestAuthenticator();
    }
}

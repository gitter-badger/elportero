package org.bitionaire.elportero.auth;

import java.security.Principal;

public interface ApiClient extends Principal {

    public default String getId() {
        return this.getName();
    }

}

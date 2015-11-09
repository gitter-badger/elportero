package org.bitionaire.elportero;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import org.bitionaire.elportero.auth.ApiClientAuthenticatorFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//import org.bitionaire.elportero.auth.ApiClientAuthenticatorConfiguration;

public class ElPorteroConfiguration extends Configuration {

    @Valid @NotNull
    @JsonProperty("authenticator")
    @Getter private ApiClientAuthenticatorFactory authenticator;

}

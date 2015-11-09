package org.bitionaire.elportero;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import lombok.Getter;
import org.bitionaire.elportero.auth.ApiClientAuthenticatorFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URL;

public class ElPorteroConfiguration extends Configuration {

    @Valid @NotNull
    @JsonProperty("authenticator")
    @Getter private ApiClientAuthenticatorFactory authenticator;

    @JsonProperty("httpClient")
    @Valid @NotNull
    @Getter private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("kong")
    @Valid @NotNull
    @Getter private URL kongUrl;

}

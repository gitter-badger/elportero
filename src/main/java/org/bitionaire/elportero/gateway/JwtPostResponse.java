package org.bitionaire.elportero.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;

@JsonSnakeCase @JsonIgnoreProperties(ignoreUnknown = true)
public class JwtPostResponse {

    @JsonProperty
    @Getter  private String key;

    @JsonProperty
    @Getter private String secret;

}

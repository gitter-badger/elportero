package org.bitionaire.elportero.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BearerApiToken implements ApiToken {

    @JsonProperty("bearer")
    @Getter private String bearer;
}

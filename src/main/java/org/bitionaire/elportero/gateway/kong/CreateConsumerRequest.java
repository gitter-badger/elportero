package org.bitionaire.elportero.gateway.kong;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bitionaire.elportero.auth.ApiClient;

public class CreateConsumerRequest {

    @JsonProperty
    private String username;

    public CreateConsumerRequest(final ApiClient apiClient) {
        this.username = apiClient.getId();
    }

}

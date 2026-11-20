package com.pisethjavaschool.platform.propertyowner.client.impl;

import java.util.UUID;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerProvisioningClient;
import com.pisethjavaschool.platform.propertyowner.client.config.PropertyOwnerClientProperties;
import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerCreatedResponse;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;
import reactor.core.publisher.Mono;

public final class DefaultPropertyOwnerProvisioningClient implements PropertyOwnerProvisioningClient {
    private final WebClient webClient;
    private final String rootPath;
    private final ProvisioningAccessTokenProvider tokenProvider;

    public DefaultPropertyOwnerProvisioningClient(WebClient.Builder builder,
            PropertyOwnerClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
        this.tokenProvider = java.util.Objects.requireNonNull(tokenProvider, "tokenProvider");
    }

    @Override
    public Mono<UUID> createOwner(CreatePropertyOwnerCommand command) {
        return tokenProvider.token()
                .flatMap(token -> webClient.post()
                        .uri(rootPath + "/internal")
                        .headers(headers -> headers.setBearerAuth(token))
                        .bodyValue(command)
                        .retrieve()
                        .bodyToMono(PropertyOwnerCreatedResponse.class)
                        .map(PropertyOwnerCreatedResponse::organizationId));
    }
}

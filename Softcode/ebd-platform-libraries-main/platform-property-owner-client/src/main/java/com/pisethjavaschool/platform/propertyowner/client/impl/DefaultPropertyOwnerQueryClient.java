package com.pisethjavaschool.platform.propertyowner.client.impl;

import java.util.UUID;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerQueryClient;
import com.pisethjavaschool.platform.propertyowner.client.config.PropertyOwnerClientProperties;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;
import com.pisethjavaschool.platform.security.CurrentUserSupport;
import reactor.core.publisher.Mono;

public final class DefaultPropertyOwnerQueryClient implements PropertyOwnerQueryClient {
    private final WebClient webClient;
    private final String rootPath;

    public DefaultPropertyOwnerQueryClient(WebClient.Builder builder, PropertyOwnerClientProperties properties) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
    }

    @Override
    public Mono<PropertyOwnerSummary> getByUserId(UUID userId) {
        return CurrentUserSupport.currentBearerToken()
                .flatMap(token -> webClient.get()
                        .uri(rootPath + "/by-user/{userId}", userId)
                        .headers(headers -> headers.setBearerAuth(token))
                        .retrieve()
                        .bodyToMono(PropertyOwnerSummary.class));
    }
}

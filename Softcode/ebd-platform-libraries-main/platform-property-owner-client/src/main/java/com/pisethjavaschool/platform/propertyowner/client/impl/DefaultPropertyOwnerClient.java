package com.pisethjavaschool.platform.propertyowner.client.impl;

import java.util.UUID;

import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerClient;
import com.pisethjavaschool.platform.propertyowner.client.config.PropertyOwnerClientProperties;
import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerCreatedResponse;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;
import com.pisethjavaschool.platform.security.CurrentUserSupport;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;

import reactor.core.publisher.Mono;
public class DefaultPropertyOwnerClient implements PropertyOwnerClient {
    private final WebClient webClient;
    private final String rootPath;
    private final ProvisioningAccessTokenProvider tokenProvider; // NEW

    public DefaultPropertyOwnerClient(WebClient.Builder builder, PropertyOwnerClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<UUID> createOwner(CreatePropertyOwnerCommand command) {
        return tokenProvider.token().flatMap(token -> webClient.post()
                .uri(rootPath + "/internal")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(command)
                .retrieve()
                .bodyToMono(PropertyOwnerCreatedResponse.class)
                .map(PropertyOwnerCreatedResponse::organizationId));
    }

    /*
    @Override
    public Mono<UUID> createOwner(CreatePropertyOwnerCommand command) {
        return webClient.post()
                .uri(rootPath + "/internal")
                .bodyValue(command)
                .retrieve()
                .bodyToMono(PropertyOwnerCreatedResponse.class)
                .map(PropertyOwnerCreatedResponse::organizationId);
    }
    */
    
    @Override
    public Mono<PropertyOwnerSummary> getByUserId(UUID userId) {
        return CurrentUserSupport.currentBearerToken().defaultIfEmpty("").flatMap(token -> {
            WebClient.RequestHeadersSpec<?> request = webClient.get()
                    .uri(rootPath + "/by-user/{userId}", userId);
            if (!token.isBlank()) {
                request = request.headers(headers -> headers.setBearerAuth(token));
            }
            return request.retrieve().bodyToMono(PropertyOwnerSummary.class);
        });
    }

    
}




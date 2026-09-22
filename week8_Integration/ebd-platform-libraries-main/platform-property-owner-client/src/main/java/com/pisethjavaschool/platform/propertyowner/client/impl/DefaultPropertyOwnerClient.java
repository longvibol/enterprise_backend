package com.pisethjavaschool.platform.propertyowner.client.impl;

import java.util.UUID;

import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerClient;
import com.pisethjavaschool.platform.propertyowner.client.config.PropertyOwnerClientProperties;
import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerCreatedResponse;

import reactor.core.publisher.Mono;
public class DefaultPropertyOwnerClient implements PropertyOwnerClient {
    private final WebClient webClient;
    private final String rootPath;

    public DefaultPropertyOwnerClient(WebClient.Builder builder, PropertyOwnerClientProperties properties) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
    }

    @Override
    public Mono<UUID> createOwner(CreatePropertyOwnerCommand command) {
        return webClient.post()
                .uri(rootPath + "/internal")
                .bodyValue(command)
                .retrieve()
                .bodyToMono(PropertyOwnerCreatedResponse.class)
                .map(PropertyOwnerCreatedResponse::organizationId);
    }
}
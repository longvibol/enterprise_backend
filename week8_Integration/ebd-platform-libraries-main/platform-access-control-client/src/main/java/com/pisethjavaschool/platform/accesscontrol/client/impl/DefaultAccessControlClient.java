package com.pisethjavaschool.platform.accesscontrol.client.impl;

import java.util.UUID;

import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.config.AccessControlClientProperties;
import com.pisethjavaschool.platform.accesscontrol.client.dto.AssignRoleByCodeRequest;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;

import reactor.core.publisher.Mono;

public class DefaultAccessControlClient implements AccessControlClient {
    private final WebClient webClient;
    private final String rootPath;

    public DefaultAccessControlClient(WebClient.Builder builder, AccessControlClientProperties properties) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
    }

    @Override
    public Mono<Void> assignRole(UUID userId, AccessRoleCode roleCode, UUID scopeId) {
        return webClient.post()
                .uri(rootPath + "/users/{userId}/roles/by-code", userId)
                .bodyValue(new AssignRoleByCodeRequest(roleCode, scopeId))
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}

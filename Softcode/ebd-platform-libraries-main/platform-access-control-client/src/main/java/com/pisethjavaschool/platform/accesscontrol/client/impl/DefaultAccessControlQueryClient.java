package com.pisethjavaschool.platform.accesscontrol.client.impl;

import java.util.UUID;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisethjavaschool.platform.accesscontrol.client.AccessControlQueryClient;
import com.pisethjavaschool.platform.accesscontrol.client.config.AccessControlClientProperties;
import com.pisethjavaschool.platform.accesscontrol.client.dto.PermissionCheckRequest;
import com.pisethjavaschool.platform.accesscontrol.client.dto.PermissionCheckResponse;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;
import com.pisethjavaschool.platform.security.CurrentUserSupport;
import reactor.core.publisher.Mono;

public final class DefaultAccessControlQueryClient implements AccessControlQueryClient {
    private final WebClient webClient;
    private final String rootPath;

    public DefaultAccessControlQueryClient(WebClient.Builder builder, AccessControlClientProperties properties) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
    }

    @Override
    public Mono<Boolean> hasPermission(UUID userId, String permissionCode, AccessScopeType scopeType, UUID scopeId) {
        var body = new PermissionCheckRequest(userId, permissionCode, scopeType, scopeId);
        return CurrentUserSupport.currentBearerToken()
                .switchIfEmpty(Mono.error(new IllegalStateException("Authenticated user bearer token required")))
                .flatMap(token -> webClient.post()
                        .uri(rootPath + "/check-permission")
                        .headers(headers -> headers.setBearerAuth(token))
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(PermissionCheckResponse.class))
                .map(PermissionCheckResponse::allowed);
    }
}


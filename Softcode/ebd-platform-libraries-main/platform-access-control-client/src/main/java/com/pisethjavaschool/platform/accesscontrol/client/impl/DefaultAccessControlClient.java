package com.pisethjavaschool.platform.accesscontrol.client.impl;

import java.util.UUID;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.config.AccessControlClientProperties;
import com.pisethjavaschool.platform.accesscontrol.client.dto.AssignRoleByCodeRequest;
import com.pisethjavaschool.platform.accesscontrol.client.dto.PermissionCheckRequest;
import com.pisethjavaschool.platform.accesscontrol.client.dto.PermissionCheckResponse;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;

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
    
    @Override
    public Mono<Boolean> hasPermission(UUID userId, String permissionCode, AccessScopeType scopeType, UUID scopeId) {
        var body = new PermissionCheckRequest(userId, permissionCode, scopeType, scopeId);
        return currentBearerToken().defaultIfEmpty("").flatMap(token -> {
            WebClient.RequestBodySpec request = webClient.post().uri(rootPath + "/check-permission");
            if (!token.isBlank()) {
                request.headers(headers -> headers.setBearerAuth(token));
            }
            return request.bodyValue(body).retrieve().bodyToMono(PermissionCheckResponse.class);
        }).map(PermissionCheckResponse::allowed);
    }

    private Mono<String> currentBearerToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .ofType(JwtAuthenticationToken.class)
                .map(authentication -> authentication.getToken().getTokenValue());
    }
}

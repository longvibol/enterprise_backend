package com.pisethjavaschool.platform.user.client.impl;

import java.util.UUID;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.security.CurrentUserSupport;
import com.pisethjavaschool.platform.user.client.PlatformUserClient;
import com.pisethjavaschool.platform.user.client.config.PlatformUserClientProperties;
import com.pisethjavaschool.platform.user.client.dto.UserIdentityResponse;
import com.pisethjavaschool.platform.security.CurrentUserSupport;

import reactor.core.publisher.Mono;
public class DefaultPlatformUserClient implements PlatformUserClient {
    private final WebClient webClient;
    private final String rootPath;

    public DefaultPlatformUserClient(WebClient.Builder builder, PlatformUserClientProperties properties) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
    }

    @Override
    public Mono<UUID> resolvePlatformUserId(UUID keycloakUserId) {
        return CurrentUserSupport.currentBearerToken()
                .defaultIfEmpty("")
                .flatMap(token -> {
                    WebClient.RequestHeadersSpec<?> request = webClient.get()
                            .uri(rootPath + "/internal/by-keycloak-id/{keycloakUserId}", keycloakUserId);
                    if (!token.isBlank()) {
                        request = request.headers(headers -> headers.setBearerAuth(token));
                    }
                    return request.retrieve().bodyToMono(UserIdentityResponse.class);
                })
                .map(UserIdentityResponse::id);
    }

    
}
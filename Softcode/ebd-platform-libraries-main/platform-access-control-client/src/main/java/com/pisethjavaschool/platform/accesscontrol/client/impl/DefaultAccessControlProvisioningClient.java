package com.pisethjavaschool.platform.accesscontrol.client.impl;

import java.util.UUID;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisethjavaschool.platform.accesscontrol.client.AccessControlProvisioningClient;
import com.pisethjavaschool.platform.accesscontrol.client.config.AccessControlClientProperties;
import com.pisethjavaschool.platform.accesscontrol.client.dto.AssignRoleByCodeRequest;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;
import reactor.core.publisher.Mono;

public final class DefaultAccessControlProvisioningClient implements AccessControlProvisioningClient {
    private final WebClient webClient;
    private final String rootPath;
    private final ProvisioningAccessTokenProvider tokenProvider;

    public DefaultAccessControlProvisioningClient(WebClient.Builder builder,
            AccessControlClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        this.webClient = builder.baseUrl(properties.getBaseUrl()).build();
        this.rootPath = properties.getRootPath();
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Void> assignRole(UUID userId, AccessRoleCode roleCode, UUID scopeId) {
        return tokenProvider.token().flatMap(token -> webClient.post()
                .uri(rootPath + "/internal/provisioning/users/{userId}/roles/by-code", userId)
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(new AssignRoleByCodeRequest(roleCode, scopeId))
                .retrieve()
                .toBodilessEntity()
                .then());
    }
}

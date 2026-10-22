package com.pisethjavaschool.platform.security;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import reactor.core.publisher.Mono;

/** Spring-managed client-credentials flow with authorized-client lifecycle and expiry handling. */
public final class ProvisioningAccessTokenProvider {
    private final ReactiveOAuth2AuthorizedClientManager manager;
    private final String registrationId;
    public ProvisioningAccessTokenProvider(ReactiveOAuth2AuthorizedClientManager manager, String registrationId) {
        this.manager = manager;
        this.registrationId = registrationId;
    }
    
    public Mono<String> token() {
        return manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId(registrationId)
                        .principal("user-service-provisioning").build())
                .switchIfEmpty(Mono.error(new IllegalStateException("Provisioning OAuth2 authorization failed")))
                .map(client -> client.getAccessToken().getTokenValue());
    }
}
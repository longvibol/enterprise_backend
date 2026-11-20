package com.pisethjavaschool.platform.security;

import java.util.Objects;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/** Authorization for a narrowly scoped OAuth2 machine identity, never a human JWT. */
public final class ServiceClientAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final String clientId;
    private final String audience;
    private final String authority;

    public ServiceClientAuthorizationManager(String clientId, String audience, String authority) {
        this.clientId = Objects.requireNonNull(clientId);
        this.audience = Objects.requireNonNull(audience);
        this.authority = Objects.requireNonNull(authority);
    }

    @Override
    public Mono<AuthorizationResult> authorize(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(this::authorized)
                .map(allowed -> (AuthorizationResult) new AuthorizationDecision(allowed))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean authorized(Authentication authentication) {
        if (!(authentication instanceof JwtAuthenticationToken jwt) || !authentication.isAuthenticated()) return false;
        var token = jwt.getToken();
        return clientId.equals(token.getClaimAsString("azp"))
                && token.getAudience().contains(audience)
                && jwt.getAuthorities().stream().anyMatch(a -> authority.equals(a.getAuthority()));
    }
}

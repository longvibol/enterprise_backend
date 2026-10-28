package com.pisethjavaschool.property.security;

import com.pisethjavaschool.platform.security.CurrentUserSupport;
import java.util.UUID;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/** Requires a validated human JWT with a well-formed platform identity claim. */
@Component
public final class PlatformUserAuthorizationManager
        implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationResult> authorize(
            Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(this::hasPlatformIdentity)
                .map(allowed -> (AuthorizationResult) new AuthorizationDecision(allowed))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean hasPlatformIdentity(Authentication authentication) {
        if (!(authentication instanceof JwtAuthenticationToken jwt) || !authentication.isAuthenticated()) {
            return false;
        }
        return isValidUuid(jwt.getToken().getClaimAsString(CurrentUserSupport.PLATFORM_USER_ID_CLAIM));
    }

    private boolean isValidUuid(String claim) {
        if (claim == null || claim.isBlank()) {
            return false;
        }
        try {
            UUID.fromString(claim);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}

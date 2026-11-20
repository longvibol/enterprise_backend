package com.pisethjavaschool.platform.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.pisethjavaschool.platform.exception.UnauthorizedException;

import reactor.core.publisher.Mono;

public final class CurrentUserSupport {
	public static final String PLATFORM_USER_ID_CLAIM = "platform_user_id"; // NEW
	private CurrentUserSupport() {
	}

	public static Mono<String> currentUsername() {
		return ReactiveSecurityContextHolder.getContext().map(c -> c.getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getName);
	}
	
	public static Mono<String> currentBearerToken() { 
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .ofType(JwtAuthenticationToken.class)
                .map(authentication -> authentication.getToken().getTokenValue());
    }
	
	/** Reads only the platform identity claim from an authenticated, validated JWT. */
    public static Mono<UUID> currentPlatformUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .ofType(JwtAuthenticationToken.class)
                .filter(Authentication::isAuthenticated)
                .switchIfEmpty(Mono.error(new UnauthorizedException(
                        "PLATFORM_USER_ID_REQUIRED", "Authenticated JWT with platform_user_id is required")))
                .flatMap(authentication -> {
                    String claim = authentication.getToken().getClaimAsString(PLATFORM_USER_ID_CLAIM);
                    if (claim == null || claim.isBlank()) {
                        return Mono.error(new UnauthorizedException(
                                "PLATFORM_USER_ID_REQUIRED", "JWT platform_user_id claim is required"));
                    }
                    try {
                        return Mono.just(UUID.fromString(claim));
                    } catch (IllegalArgumentException exception) {
                        return Mono.error(new UnauthorizedException(
                                "INVALID_PLATFORM_USER_ID", "JWT platform_user_id claim must be a UUID"));
                    }
                });
    }
}

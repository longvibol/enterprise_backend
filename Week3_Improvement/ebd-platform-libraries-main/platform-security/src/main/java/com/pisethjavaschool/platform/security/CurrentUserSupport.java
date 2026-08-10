package com.pisethjavaschool.platform.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public final class CurrentUserSupport {
	private CurrentUserSupport() {
	}

	public static Mono<String> currentUsername() {
		return ReactiveSecurityContextHolder.getContext().map(c -> c.getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getName);
	}
}

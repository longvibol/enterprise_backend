package com.pisethjavaschool.platform.security.audit;

import java.util.UUID;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityCurrentAuditorProvider
        implements CurrentAuditorProvider {

    @Override
    public Mono<UUID> getCurrentAuditorId() {

        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .map(UUID::fromString)
                .onErrorResume(ex -> Mono.empty());
    }
}
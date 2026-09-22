package com.pisethjavaschool.platform.security.audit;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;
import com.pisethjavaschool.platform.security.PlatformAuditorContext;
import com.pisethjavaschool.platform.security.PlatformUserIdResolver;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
/*
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
*/

@RequiredArgsConstructor
public class SecurityCurrentAuditorProvider implements CurrentAuditorProvider {

    private final PlatformUserIdResolver platformUserIdResolver;

    @Override
    public Mono<UUID> getCurrentAuditorId() {
        return Mono.deferContextual(context -> {
            if (context.hasKey(PlatformAuditorContext.AUDITOR_ID_CONTEXT_KEY)) {
                return Mono.just(context.<UUID>get(PlatformAuditorContext.AUDITOR_ID_CONTEXT_KEY));
            }

            return ReactiveSecurityContextHolder.getContext()
                    .map(securityContext -> securityContext.getAuthentication())
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getName)
                    .map(UUID::fromString)
                    .flatMap(platformUserIdResolver::resolvePlatformUserId);
                    //.flatMap(keycloakUserId -> platformUserIdResolver.resolvePlatformUserId(keycloakUserId));
        });
    }
}
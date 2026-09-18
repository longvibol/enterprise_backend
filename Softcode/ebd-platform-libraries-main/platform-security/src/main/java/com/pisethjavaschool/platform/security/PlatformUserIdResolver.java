package com.pisethjavaschool.platform.security;

import java.util.UUID;

import reactor.core.publisher.Mono;
@FunctionalInterface
public interface PlatformUserIdResolver {
    Mono<UUID> resolvePlatformUserId(UUID keycloakUserId);
}
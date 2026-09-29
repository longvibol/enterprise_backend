package com.pisethjavaschool.property.facade;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface ChangePropertyStatusFacade {
    Mono<Void> submit(UUID id);
    Mono<Void> approve(UUID id);
    Mono<Void> reject(UUID id, String reason);
    Mono<Void> suspend(UUID id);
    Mono<Void> activate(UUID id);
    Mono<Void> deactivate(UUID id);
}

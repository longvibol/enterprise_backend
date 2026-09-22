package com.pisethjavaschool.propertyowner.facade;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface ChangePropertyOwnerStatusFacade {
	Mono<Void> submit(UUID id);

	Mono<Void> verify(UUID id);

	Mono<Void> reject(UUID id, String reason);

	Mono<Void> suspend(UUID id);

	Mono<Void> activate(UUID id);

	Mono<Void> deactivate(UUID id);
}

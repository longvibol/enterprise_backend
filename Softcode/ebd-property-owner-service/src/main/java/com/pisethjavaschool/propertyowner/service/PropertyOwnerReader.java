package com.pisethjavaschool.propertyowner.service;

import java.util.Collection;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PropertyOwnerReader {
	Mono<PropertyOwner> getById(UUID id);

	Mono<PropertyOwner> getByUserId(UUID userId);

	Flux<PropertyOwner> getByUserIds(Collection<UUID> userIds);

	Mono<PropertyOwner> getOwnedOwner(UUID ownerId, UUID userId);
}

package com.pisethjavaschool.propertyowner.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PropertyOwnerRepository extends ReactiveCrudRepository<PropertyOwner, UUID> {
	Mono<PropertyOwner> findByUserId(UUID userId);

	Flux<PropertyOwner> findByUserIdIn(Collection<UUID> userIds);

	Mono<Boolean> existsByUserId(UUID userId);
}

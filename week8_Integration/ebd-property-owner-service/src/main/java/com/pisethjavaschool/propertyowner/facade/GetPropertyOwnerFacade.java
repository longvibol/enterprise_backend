package com.pisethjavaschool.propertyowner.facade;

import java.util.Collection;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetPropertyOwnerFacade {
    Mono<PropertyOwnerResponse> getById(UUID id);

    Mono<PropertyOwnerResponse> getByUserId(UUID userId);

    Flux<PropertyOwnerResponse> getByUserIds(
            Collection<UUID> userIds
    );

    Mono<PropertyOwnerResponse> getMe();
}

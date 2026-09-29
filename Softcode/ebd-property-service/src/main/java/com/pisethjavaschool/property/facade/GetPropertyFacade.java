package com.pisethjavaschool.property.facade;

import java.util.UUID;

import com.pisethjavaschool.property.dto.PropertyResponse;

import reactor.core.publisher.Mono;

public interface GetPropertyFacade {
    Mono<PropertyResponse> getById(UUID id);
}

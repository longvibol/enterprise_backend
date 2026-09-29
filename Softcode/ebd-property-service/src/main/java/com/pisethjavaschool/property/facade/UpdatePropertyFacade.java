package com.pisethjavaschool.property.facade;

import java.util.UUID;

import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.dto.UpdatePropertyRequest;

import reactor.core.publisher.Mono;

public interface UpdatePropertyFacade {
    Mono<PropertyResponse> update(UUID id, UpdatePropertyRequest request);
}

package com.pisethjavaschool.property.facade;

import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.dto.PropertyResponse;

import reactor.core.publisher.Mono;

public interface CreatePropertyFacade {
    Mono<PropertyResponse> create(CreatePropertyRequest request);
}

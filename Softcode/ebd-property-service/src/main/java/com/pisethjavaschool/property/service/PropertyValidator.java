package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Mono;

public interface PropertyValidator {

    Mono<Void> validateForCreate(CreatePropertyRequest request);

    Mono<Void> validateForUpdate(Property property, UpdatePropertyRequest request);
}

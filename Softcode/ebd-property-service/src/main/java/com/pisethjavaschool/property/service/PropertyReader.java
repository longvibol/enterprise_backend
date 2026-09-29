package com.pisethjavaschool.property.service;

import java.util.UUID;

import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Mono;

public interface PropertyReader {
    Mono<Property> getById(UUID id);
    Mono<Property> getOwnedProperty(UUID id, UUID ownerId);
}

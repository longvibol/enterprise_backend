package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Mono;

public interface PropertyWriter {
    Mono<Property> save(Property property);
}

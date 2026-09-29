package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Mono;

public interface PropertyEditPolicy {

    Mono<Void> validateEditable(Property property);
}
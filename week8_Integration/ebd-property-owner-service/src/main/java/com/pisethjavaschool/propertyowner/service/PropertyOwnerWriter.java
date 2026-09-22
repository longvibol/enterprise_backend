package com.pisethjavaschool.propertyowner.service;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

import reactor.core.publisher.Mono;

public interface PropertyOwnerWriter {
    Mono<PropertyOwner> save(PropertyOwner propertyOwner);
}

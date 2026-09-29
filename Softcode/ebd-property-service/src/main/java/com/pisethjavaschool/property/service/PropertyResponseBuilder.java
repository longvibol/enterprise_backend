
package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Mono;

public interface PropertyResponseBuilder {

    Mono<PropertyResponse> build(Property property);
}

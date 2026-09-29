
package com.pisethjavaschool.property.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.facade.GetPropertyFacade;
import com.pisethjavaschool.property.service.PropertyReader;
import com.pisethjavaschool.property.service.PropertyResponseBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetPropertyFacadeImpl implements GetPropertyFacade {

    private final PropertyReader reader;
    private final PropertyResponseBuilder responseBuilder;

    @Override
    public Mono<PropertyResponse> getById(UUID id) {
        return reader.getById(id)
                .flatMap(responseBuilder::build);
    }
}

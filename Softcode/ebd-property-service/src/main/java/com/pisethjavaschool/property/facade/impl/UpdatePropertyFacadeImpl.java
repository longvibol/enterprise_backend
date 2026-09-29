package com.pisethjavaschool.property.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.facade.UpdatePropertyFacade;
import com.pisethjavaschool.property.service.PropertyEditPolicy;
import com.pisethjavaschool.property.service.PropertyImageWriter;
import com.pisethjavaschool.property.service.PropertyReader;
import com.pisethjavaschool.property.service.PropertyResponseBuilder;
import com.pisethjavaschool.property.service.PropertyUpdater;
import com.pisethjavaschool.property.service.PropertyValidator;
import com.pisethjavaschool.property.service.PropertyWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdatePropertyFacadeImpl implements UpdatePropertyFacade {

    private final PropertyReader reader;
    private final PropertyEditPolicy editPolicy;
    private final PropertyValidator validator;
    private final PropertyUpdater updater;
    private final PropertyWriter writer;
    private final PropertyImageWriter imageWriter;
    private final PropertyResponseBuilder responseBuilder;

    @Override
    @Transactional
    public Mono<PropertyResponse> update(
            UUID id,
            UpdatePropertyRequest request) {

        return reader.getById(id)
                .flatMap(property -> editPolicy.validateEditable(property)
                        .then(validator.validateForUpdate(property, request))
                        .thenReturn(property))
                .map(property -> updater.apply(property, request))
                .flatMap(writer::save)
                .flatMap(property -> imageWriter
                        .replaceForUpdate(
                                property.getId(),
                                request.thumbnailMediaId(),
                                request.galleryMediaIds())
                        .thenReturn(property))
                .flatMap(responseBuilder::build);
    }
}

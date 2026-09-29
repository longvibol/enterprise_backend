
package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.mapper.PropertyMapper;
import com.pisethjavaschool.property.repository.PropertyImageRepository;
import com.pisethjavaschool.property.service.PropertyResponseBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyResponseBuilderImpl implements PropertyResponseBuilder {

    private final PropertyMapper mapper;
    private final PropertyImageRepository imageRepository;

    @Override
    public Mono<PropertyResponse> build(Property property) {
        return imageRepository.findByPropertyIdOrderBySortOrderAsc(property.getId())
                .map(mapper::toImageResponse)
                .collectList()
                .map(images -> mapper.toResponse(property, images));
    }
}

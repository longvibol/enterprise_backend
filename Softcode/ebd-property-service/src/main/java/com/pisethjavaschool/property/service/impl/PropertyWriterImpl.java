package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.repository.PropertyRepository;
import com.pisethjavaschool.property.service.PropertyWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyWriterImpl implements PropertyWriter {
    private final PropertyRepository repository;

    @Override
    public Mono<Property> save(Property property) {
        return repository.save(property);
    }
}

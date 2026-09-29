package com.pisethjavaschool.property.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.exception.PropertyAccessDeniedException;
import com.pisethjavaschool.property.exception.PropertyNotFoundException;
import com.pisethjavaschool.property.repository.PropertyRepository;
import com.pisethjavaschool.property.service.PropertyReader;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyReaderImpl implements PropertyReader {
    private final PropertyRepository repository;

    @Override
    public Mono<Property> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new PropertyNotFoundException("Property not found: " + id)));
    }

    @Override
    public Mono<Property> getOwnedProperty(UUID id, UUID ownerId) {
        return getById(id)
                .filter(property -> ownerId.equals(property.getOwnerId()))
                .switchIfEmpty(Mono.error(new PropertyAccessDeniedException("You do not own this property")));
    }
}

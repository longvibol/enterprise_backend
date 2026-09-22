package com.pisethjavaschool.propertyowner.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.repository.PropertyOwnerRepository;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyOwnerWriterImpl implements PropertyOwnerWriter {
    private final PropertyOwnerRepository repository;

    @Override
    public Mono<PropertyOwner> save(PropertyOwner propertyOwner) {
        return repository.save(propertyOwner);
    }
}

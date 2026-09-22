package com.pisethjavaschool.propertyowner.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.exception.PropertyOwnerAlreadyExistsException;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentTypeRepository;
import com.pisethjavaschool.propertyowner.repository.PropertyOwnerRepository;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyOwnerValidatorImpl implements PropertyOwnerValidator {
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final OwnerDocumentTypeRepository documentTypeRepository;

    @Override
    public Mono<Void> validateUserHasNoOwner(UUID userId) {
        return propertyOwnerRepository.existsByUserId(userId)
                .flatMap(exists -> exists ? Mono.error(new PropertyOwnerAlreadyExistsException()) : Mono.empty());
    }

    @Override
    public Mono<Void> validateDocumentType(String documentTypeCode) {
        return documentTypeRepository.existsByCodeAndActiveTrue(documentTypeCode.trim().toUpperCase())
                .flatMap(exists -> exists ? Mono.empty() : Mono.error(new IllegalArgumentException("Invalid owner document type code")));
    }
}

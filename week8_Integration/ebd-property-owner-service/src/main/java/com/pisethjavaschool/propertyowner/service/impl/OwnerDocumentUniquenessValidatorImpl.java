
package com.pisethjavaschool.propertyowner.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.platform.exception.ConflictException;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentRepository;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentUniquenessValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OwnerDocumentUniquenessValidatorImpl
        implements OwnerDocumentUniquenessValidator {

    private final OwnerDocumentRepository repository;

    @Override
    public Mono<Void> validateForCreate(
            UUID ownerId,
            String documentTypeCode
    ) {
        String normalized = normalize(documentTypeCode);

        return repository
                .existsByOwnerIdAndDocumentTypeCodeAndActiveTrue(
                        ownerId,
                        normalized
                )
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? duplicate(normalized)
                        : Mono.empty());
    }

    @Override
    public Mono<Void> validateForUpdate(
            UUID ownerId,
            UUID documentId,
            String documentTypeCode
    ) {
        String normalized = normalize(documentTypeCode);

        return repository
                .existsByOwnerIdAndDocumentTypeCodeAndActiveTrueAndIdNot(
                        ownerId,
                        normalized,
                        documentId
                )
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? duplicate(normalized)
                        : Mono.empty());
    }

    private Mono<Void> duplicate(String documentTypeCode) {
        return Mono.error(new ConflictException(
                "OWNER_DOCUMENT_TYPE_ALREADY_EXISTS",
                "An active " + documentTypeCode
                        + " document already exists for this owner."
        ));
    }

    private String normalize(String value) {
        return value.trim().toUpperCase();
    }
}

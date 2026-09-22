
package com.pisethjavaschool.propertyowner.service.impl;

import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pisethjavaschool.platform.exception.ConflictException;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class OwnerDocumentUniquenessValidatorImplTest {

    @Mock
    private OwnerDocumentRepository repository;

    private OwnerDocumentUniquenessValidatorImpl validator;

    @BeforeEach
    void setUp() {
        validator = new OwnerDocumentUniquenessValidatorImpl(repository);
    }

    @Test
    void createShouldRejectDuplicateActiveDocumentType() {
        UUID ownerId = UUID.randomUUID();

        when(repository.existsByOwnerIdAndDocumentTypeCodeAndActiveTrue(
                ownerId,
                "NATIONAL_ID"
        )).thenReturn(Mono.just(true));

        StepVerifier.create(
                        validator.validateForCreate(
                                ownerId,
                                "national_id"
                        )
                )
                .expectErrorMatches(error ->
                        error instanceof ConflictException
                                && "OWNER_DOCUMENT_TYPE_ALREADY_EXISTS".equals(
                                        ((ConflictException) error).getErrorCode()
                                )
                )
                .verify();
    }

    @Test
    void createShouldAllowMissingDocumentType() {
        UUID ownerId = UUID.randomUUID();

        when(repository.existsByOwnerIdAndDocumentTypeCodeAndActiveTrue(
                ownerId,
                "NATIONAL_ID"
        )).thenReturn(Mono.just(false));

        StepVerifier.create(
                        validator.validateForCreate(
                                ownerId,
                                "NATIONAL_ID"
                        )
                )
                .verifyComplete();
    }
}

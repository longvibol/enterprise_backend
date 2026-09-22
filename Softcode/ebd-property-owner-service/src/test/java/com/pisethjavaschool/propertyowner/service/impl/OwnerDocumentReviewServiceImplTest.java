
package com.pisethjavaschool.propertyowner.service.impl;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pisethjavaschool.propertyowner.entity.OwnerDocument;
import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class OwnerDocumentReviewServiceImplTest {

    @Mock
    private OwnerDocumentRepository repository;

    private OwnerDocumentReviewServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OwnerDocumentReviewServiceImpl(repository);
    }

    @Test
    void verifyPendingDocumentsShouldMarkActivePendingDocumentsVerified() {
        UUID ownerId = UUID.randomUUID();

        OwnerDocument nationalId = OwnerDocument.builder()
                .id(UUID.randomUUID())
                .ownerId(ownerId)
                .status(OwnerDocumentStatus.PENDING_REVIEW)
                .active(true)
                .rejectionReason("old reason")
                .build();

        OwnerDocument businessDocument = OwnerDocument.builder()
                .id(UUID.randomUUID())
                .ownerId(ownerId)
                .status(OwnerDocumentStatus.PENDING_REVIEW)
                .active(true)
                .build();

        when(repository.findByOwnerIdAndActiveTrueAndStatus(
                ownerId,
                OwnerDocumentStatus.PENDING_REVIEW
        )).thenReturn(Flux.just(nationalId, businessDocument));

        when(repository.saveAll(anyList()))
                .thenAnswer(invocation ->
                        Flux.fromIterable(invocation.getArgument(0)));

        StepVerifier.create(service.verifyPendingDocuments(ownerId))
                .verifyComplete();

        org.junit.jupiter.api.Assertions.assertEquals(
                OwnerDocumentStatus.VERIFIED,
                nationalId.getStatus()
        );
        org.junit.jupiter.api.Assertions.assertNull(
                nationalId.getRejectionReason()
        );
        org.junit.jupiter.api.Assertions.assertEquals(
                OwnerDocumentStatus.VERIFIED,
                businessDocument.getStatus()
        );

        verify(repository).saveAll(List.of(
                nationalId,
                businessDocument
        ));
    }

    @Test
    void verifyPendingDocumentsShouldCompleteWhenNothingIsPending() {
        UUID ownerId = UUID.randomUUID();

        when(repository.findByOwnerIdAndActiveTrueAndStatus(
                ownerId,
                OwnerDocumentStatus.PENDING_REVIEW
        )).thenReturn(Flux.empty());

        StepVerifier.create(service.verifyPendingDocuments(ownerId))
                .verifyComplete();
    }
}

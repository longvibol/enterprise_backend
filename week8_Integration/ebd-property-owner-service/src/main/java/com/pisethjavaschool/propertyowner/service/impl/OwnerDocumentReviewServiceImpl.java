
package com.pisethjavaschool.propertyowner.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentRepository;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentReviewService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OwnerDocumentReviewServiceImpl
        implements OwnerDocumentReviewService {

    private final OwnerDocumentRepository repository;

    @Override
    @Transactional
    public Mono<Void> verifyPendingDocuments(UUID ownerId) {
        return repository
                .findByOwnerIdAndActiveTrueAndStatus(
                        ownerId,
                        OwnerDocumentStatus.PENDING_REVIEW
                )
                .map(document -> {
                    document.setStatus(OwnerDocumentStatus.VERIFIED);
                    document.setRejectionReason(null);
                    return document;
                })
                .collectList()
                .flatMap(documents -> {
                    if (documents.isEmpty()) {
                        return Mono.empty();
                    }

                    return repository.saveAll(documents).then();
                });
    }
}

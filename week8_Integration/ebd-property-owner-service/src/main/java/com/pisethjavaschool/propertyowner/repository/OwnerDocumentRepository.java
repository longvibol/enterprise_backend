package com.pisethjavaschool.propertyowner.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.propertyowner.entity.OwnerDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OwnerDocumentRepository extends ReactiveCrudRepository<OwnerDocument, UUID> {
    Flux<OwnerDocument> findByOwnerIdOrderByCreatedAtDesc(UUID ownerId);
    Mono<OwnerDocument> findByIdAndOwnerId(UUID id, UUID ownerId);

    Mono<Boolean> existsByOwnerIdAndDocumentTypeCodeAndActiveTrue(
            UUID ownerId,
            String documentTypeCode
    );

    Mono<Boolean> existsByOwnerIdAndDocumentTypeCodeAndActiveTrueAndIdNot(
            UUID ownerId,
            String documentTypeCode,
            UUID id
    );

    Flux<OwnerDocument> findByOwnerIdAndActiveTrueAndStatus(
            UUID ownerId,
            com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus status
    );
}

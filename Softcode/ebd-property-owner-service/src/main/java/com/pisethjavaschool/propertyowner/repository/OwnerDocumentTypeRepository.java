package com.pisethjavaschool.propertyowner.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.propertyowner.entity.OwnerDocumentType;

import reactor.core.publisher.Mono;

public interface OwnerDocumentTypeRepository extends ReactiveCrudRepository<OwnerDocumentType, UUID> {
	Mono<Boolean> existsByCodeAndActiveTrue(String code);
}

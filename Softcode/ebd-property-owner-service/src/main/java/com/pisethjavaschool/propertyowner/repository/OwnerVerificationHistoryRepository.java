package com.pisethjavaschool.propertyowner.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.propertyowner.entity.OwnerVerificationHistory;

import reactor.core.publisher.Flux;

public interface OwnerVerificationHistoryRepository extends ReactiveCrudRepository<OwnerVerificationHistory, UUID> {
	Flux<OwnerVerificationHistory> findByOwnerIdOrderByReviewedAtDesc(UUID ownerId);
}

package com.pisethjavaschool.property.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.property.entity.Property;

import reactor.core.publisher.Flux;

public interface PropertyRepository extends ReactiveCrudRepository<Property, UUID> {
    Flux<Property> findByOwnerIdOrderByCreatedAtDesc(UUID ownerId);
}

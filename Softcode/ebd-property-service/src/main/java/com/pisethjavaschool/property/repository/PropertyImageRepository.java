
package com.pisethjavaschool.property.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.property.entity.PropertyImage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PropertyImageRepository extends ReactiveCrudRepository<PropertyImage, UUID> {

    Flux<PropertyImage> findByPropertyIdOrderBySortOrderAsc(UUID propertyId);

    Mono<Void> deleteByPropertyId(UUID propertyId);
}

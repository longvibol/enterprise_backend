package com.pisethjavaschool.propertyowner.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.exception.PropertyOwnerAccessDeniedException;
import com.pisethjavaschool.propertyowner.exception.PropertyOwnerNotFoundException;
import com.pisethjavaschool.propertyowner.repository.PropertyOwnerRepository;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerReader;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyOwnerReaderImpl implements PropertyOwnerReader {
    private final PropertyOwnerRepository repository;

    @Override
    public Mono<PropertyOwner> getById(UUID id) {
        return repository.findById(id).switchIfEmpty(Mono.error(new PropertyOwnerNotFoundException()));
    }

    @Override
    public Mono<PropertyOwner> getByUserId(UUID userId) {
        return repository.findByUserId(userId).switchIfEmpty(Mono.error(new PropertyOwnerNotFoundException()));
    }

    @Override
    public Flux<PropertyOwner> getByUserIds(
            Collection<UUID> userIds
    ) {
        if (userIds == null || userIds.isEmpty()) {
            return Flux.empty();
        }

        return repository.findByUserIdIn(userIds);
    }

    @Override
    public Mono<PropertyOwner> getOwnedOwner(UUID ownerId, UUID userId) {
        return getById(ownerId)
                .flatMap(owner -> owner.getUserId().equals(userId)
                        ? Mono.just(owner)
                        : Mono.error(new PropertyOwnerAccessDeniedException()));
    }
}

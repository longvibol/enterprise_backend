package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.propertyowner.dto.OwnerVerificationHistoryResponse;
import com.pisethjavaschool.propertyowner.facade.OwnerVerificationHistoryFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.repository.OwnerVerificationHistoryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class OwnerVerificationHistoryFacadeImpl implements OwnerVerificationHistoryFacade {
    private final OwnerVerificationHistoryRepository repository;
    private final PropertyOwnerMapper mapper;

    @Override
    public Flux<OwnerVerificationHistoryResponse> getByOwner(UUID ownerId) {
        return repository.findByOwnerIdOrderByReviewedAtDesc(ownerId).map(mapper::toResponse);
    }
}

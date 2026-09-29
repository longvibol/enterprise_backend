package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.exception.InvalidPropertyStatusException;
import com.pisethjavaschool.property.service.PropertyEditPolicy;

import reactor.core.publisher.Mono;

@Service
public class PropertyEditPolicyImpl implements PropertyEditPolicy {

    @Override
    public Mono<Void> validateEditable(Property property) {
        if (property.getStatus() == PropertyStatus.APPROVED
                || property.getStatus() == PropertyStatus.SUSPENDED) {
            return Mono.error(new InvalidPropertyStatusException(
                    "Only DRAFT, REJECTED, or PENDING_APPROVAL property can be edited"
            ));
        }

        return Mono.empty();
    }
}
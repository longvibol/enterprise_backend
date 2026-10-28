package com.pisethjavaschool.property.service.impl;

import java.util.Objects;
import org.springframework.stereotype.Service;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.exception.PropertyAccessDeniedException;
import com.pisethjavaschool.property.service.CurrentPropertyOwnerResolver;
import com.pisethjavaschool.property.service.PropertyOwnershipPolicy;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyOwnershipPolicyImpl implements PropertyOwnershipPolicy {
    private final CurrentPropertyOwnerResolver ownerResolver;

    @Override
    public Mono<Void> verifyOwner(Property property) {
        return ownerResolver.resolveVerifiedActiveOwner()
                .flatMap(owner -> Objects.equals(owner.id(), property.getOwnerId())
                        ? Mono.<Void>empty()
                        : Mono.error(new PropertyAccessDeniedException("Property does not belong to current owner")));
    }
}

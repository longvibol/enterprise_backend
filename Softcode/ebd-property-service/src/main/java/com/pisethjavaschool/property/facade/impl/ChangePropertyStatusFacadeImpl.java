package com.pisethjavaschool.property.facade.impl;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.exception.InvalidPropertyStatusException;
import com.pisethjavaschool.property.facade.ChangePropertyStatusFacade;
import com.pisethjavaschool.property.service.PropertyReader;
import com.pisethjavaschool.property.service.PropertyWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChangePropertyStatusFacadeImpl
        implements ChangePropertyStatusFacade {

    private final PropertyReader reader;
    private final PropertyWriter writer;

    @Override
    @Transactional
    public Mono<Void> submit(UUID id) {
        return execute(
                id,
                property -> change(
                        property,
                        PropertyStatus.PENDING_APPROVAL,
                        null,
                        PropertyStatus.DRAFT,
                        PropertyStatus.REJECTED));
    }

    @Override
    @Transactional
    public Mono<Void> activate(UUID id) {
        return execute(id, property -> {
            property.setActive(true);
            return writer.save(property).then();
        });
    }

    @Override
    @Transactional
    public Mono<Void> deactivate(UUID id) {
        return execute(id, property -> {
            property.setActive(false);
            return writer.save(property).then();
        });
    }

    @Override
    @Transactional
    public Mono<Void> approve(UUID id) {
        return execute(
                id,
                property -> change(
                        property,
                        PropertyStatus.APPROVED,
                        null,
                        PropertyStatus.PENDING_APPROVAL));
    }

    @Override
    @Transactional
    public Mono<Void> reject(UUID id, String reason) {
        return execute(
                id,
                property -> change(
                        property,
                        PropertyStatus.REJECTED,
                        reason,
                        PropertyStatus.PENDING_APPROVAL));
    }

    @Override
    @Transactional
    public Mono<Void> suspend(UUID id) {
        return execute(
                id,
                property -> change(
                        property,
                        PropertyStatus.SUSPENDED,
                        null,
                        PropertyStatus.APPROVED));
    }

    private Mono<Void> execute(
            UUID id,
            Function<Property, Mono<Void>> operation) {

        return reader.getById(id)
                .flatMap(operation);
    }

    private Mono<Void> change(
            Property property,
            PropertyStatus target,
            String reason,
            PropertyStatus... allowedStatuses) {

        if (!Arrays.asList(allowedStatuses).contains(property.getStatus())) {
            return Mono.error(new InvalidPropertyStatusException(
                    "Cannot change property status from "
                            + property.getStatus()
                            + " to "
                            + target));
        }

        property.setStatus(target);
        property.setRejectionReason(reason);

        return writer.save(property).then();
    }
}

package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.exception.InvalidPropertyTypeException;
import com.pisethjavaschool.property.service.PropertyValidator;

import reactor.core.publisher.Mono;

@Service
public class PropertyValidatorImpl implements PropertyValidator {

    @Override
    public Mono<Void> validateForCreate(CreatePropertyRequest request) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> validateForUpdate(
            Property property,
            UpdatePropertyRequest request) {

        if (property.getBusinessType() != request.businessType()) {
            return Mono.error(new InvalidPropertyTypeException(
                    "Business type cannot be changed after the property is created"));
        }

        return Mono.empty();
    }
}

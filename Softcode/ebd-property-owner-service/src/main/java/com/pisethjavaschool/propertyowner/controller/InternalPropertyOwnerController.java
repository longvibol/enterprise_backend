package com.pisethjavaschool.propertyowner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.propertyowner.dto.InternalCreatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerCreatedResponse;
import com.pisethjavaschool.propertyowner.facade.CreatePropertyOwnerFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property-owners/internal")
public class InternalPropertyOwnerController {
    private final CreatePropertyOwnerFacade createFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PropertyOwnerCreatedResponse> create(@Valid @RequestBody InternalCreatePropertyOwnerRequest request) {
        return createFacade.createForUser(request.userId(), request.toCreateRequest())
                .map(owner -> new PropertyOwnerCreatedResponse(owner.id()));
    }
}
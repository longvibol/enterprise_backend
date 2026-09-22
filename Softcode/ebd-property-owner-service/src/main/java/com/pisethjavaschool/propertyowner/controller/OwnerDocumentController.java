package com.pisethjavaschool.propertyowner.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.pisethjavaschool.propertyowner.dto.CreateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.dto.OwnerDocumentResponse;
import com.pisethjavaschool.propertyowner.dto.UpdateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.facade.OwnerDocumentFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property-owners/{ownerId}/documents")
public class OwnerDocumentController {
    private final OwnerDocumentFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OwnerDocumentResponse> create(@PathVariable UUID ownerId, @Valid @RequestBody CreateOwnerDocumentRequest request) {
        return facade.create(ownerId, request);
    }

    @PutMapping("/{documentId}")
    public Mono<OwnerDocumentResponse> update(@PathVariable UUID ownerId, @PathVariable UUID documentId, @Valid @RequestBody UpdateOwnerDocumentRequest request) {
        return facade.update(ownerId, documentId, request);
    }

    @GetMapping
    public Flux<OwnerDocumentResponse> getByOwner(@PathVariable UUID ownerId) {
        return facade.getByOwner(ownerId);
    }

    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID ownerId, @PathVariable UUID documentId) {
        return facade.delete(ownerId, documentId);
    }
}

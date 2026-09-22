package com.pisethjavaschool.propertyowner.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.pisethjavaschool.propertyowner.dto.OwnerVerificationHistoryResponse;
import com.pisethjavaschool.propertyowner.facade.OwnerVerificationHistoryFacade;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property-owners/{ownerId}/verification-history")
public class OwnerVerificationHistoryController {
    private final OwnerVerificationHistoryFacade facade;

    @GetMapping
    public Flux<OwnerVerificationHistoryResponse> getByOwner(@PathVariable UUID ownerId) {
        return facade.getByOwner(ownerId);
    }
}

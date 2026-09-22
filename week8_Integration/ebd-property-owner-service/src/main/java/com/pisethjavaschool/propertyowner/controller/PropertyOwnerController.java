package com.pisethjavaschool.propertyowner.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.propertyowner.dto.CreatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerSummaryResponse;
import com.pisethjavaschool.propertyowner.dto.RejectPropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.dto.UpdatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.facade.ChangePropertyOwnerStatusFacade;
import com.pisethjavaschool.propertyowner.facade.CreatePropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.facade.GetPropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.facade.SearchPropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.facade.UpdatePropertyOwnerFacade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property-owners")
public class PropertyOwnerController {
    private final CreatePropertyOwnerFacade createFacade;
    private final UpdatePropertyOwnerFacade updateFacade;
    private final GetPropertyOwnerFacade getFacade;
    private final SearchPropertyOwnerFacade searchFacade;
    private final ChangePropertyOwnerStatusFacade statusFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PropertyOwnerResponse> create(@Valid @RequestBody CreatePropertyOwnerRequest request) {
        return createFacade.create(request);
    }

    @PutMapping("/{id}")
    public Mono<PropertyOwnerResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdatePropertyOwnerRequest request) {
        return updateFacade.update(id, request);
    }

    @GetMapping("/by-users")
    public Flux<PropertyOwnerResponse> getByUserIds(
            @RequestParam java.util.List<UUID> userIds
    ) {
        return getFacade.getByUserIds(userIds);
    }

    @GetMapping("/by-user/{userId}")
    public Mono<PropertyOwnerResponse> getByUserId(
            @PathVariable UUID userId
    ) {
        return getFacade.getByUserId(userId);
    }

    @GetMapping("/{id}")
    public Mono<PropertyOwnerResponse> getById(@PathVariable UUID id) {
        return getFacade.getById(id);
    }

    @GetMapping("/me")
    public Mono<PropertyOwnerResponse> getMe() {
        return getFacade.getMe();
    }

    @GetMapping
    public Mono<PageResponse<PropertyOwnerSummaryResponse>> search(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) OwnerType ownerType,
            @RequestParam(required = false) OwnerVerificationStatus status,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return searchFacade.search(userId, ownerType, status, active, keyword, page, size);
    }

    @PatchMapping("/{id}/submit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> submit(@PathVariable UUID id) {
        return statusFacade.submit(id);
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> activate(@PathVariable UUID id) {
        return statusFacade.activate(id);
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deactivate(@PathVariable UUID id) {
        return statusFacade.deactivate(id);
    }

    @PatchMapping("/admin/{id}/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> verify(@PathVariable UUID id) {
        return statusFacade.verify(id);
    }

    @PatchMapping("/admin/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> reject(@PathVariable UUID id, @Valid @RequestBody RejectPropertyOwnerRequest request) {
        return statusFacade.reject(id, request.reason());
    }

    @PatchMapping("/admin/{id}/suspend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> suspend(@PathVariable UUID id) {
        return statusFacade.suspend(id);
    }
}

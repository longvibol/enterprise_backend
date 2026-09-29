package com.pisethjavaschool.property.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.dto.PropertySummaryResponse;
import com.pisethjavaschool.property.dto.RejectPropertyRequest;
import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.facade.ChangePropertyStatusFacade;
import com.pisethjavaschool.property.facade.CreatePropertyFacade;
import com.pisethjavaschool.property.facade.GetPropertyFacade;
import com.pisethjavaschool.property.facade.SearchPropertyFacade;
import com.pisethjavaschool.property.facade.UpdatePropertyFacade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private final CreatePropertyFacade createFacade;
    private final UpdatePropertyFacade updateFacade;
    private final GetPropertyFacade getFacade;
    private final SearchPropertyFacade searchFacade;
    private final ChangePropertyStatusFacade statusFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PropertyResponse> create(@Valid @RequestBody CreatePropertyRequest request) {
        return createFacade.create(request);
    }

    @PutMapping("/{id}")
    public Mono<PropertyResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdatePropertyRequest request) {
        return updateFacade.update(id, request);
    }

    @GetMapping("/{id}")
    public Mono<PropertyResponse> getById(@PathVariable UUID id) {
        return getFacade.getById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public Mono<PageResponse<PropertySummaryResponse>> ownerProperties(
            @PathVariable UUID ownerId,
            @RequestParam(required = false) PropertyStatus status,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return searchFacade.search(ownerId, status, active, keyword, page, size);
    }

    @GetMapping
    public Mono<PageResponse<PropertySummaryResponse>> search(
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) PropertyStatus status,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return searchFacade.search(ownerId, status, active, keyword, page, size);
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

    @PatchMapping("/admin/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> approve(@PathVariable UUID id) {
        return statusFacade.approve(id);
    }

    @PatchMapping("/admin/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> reject(@PathVariable UUID id, @Valid @RequestBody RejectPropertyRequest request) {
        return statusFacade.reject(id, request.reason());
    }

    @PatchMapping("/admin/{id}/suspend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> suspend(@PathVariable UUID id) {
        return statusFacade.suspend(id);
    }
}

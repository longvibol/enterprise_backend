package com.pisethjavaschool.accesscontrol.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.accesscontrol.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.dto.RoleResponse;
import com.pisethjavaschool.accesscontrol.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.dto.UpdateStatusRequest;
import com.pisethjavaschool.accesscontrol.facade.RoleManagementFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-control/roles")
public class RoleController {

    private final RoleManagementFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoleResponse> create(@Valid @RequestBody CreateRoleRequest request) {
        return facade.create(request);
    }

    @GetMapping
    public Flux<RoleResponse> getAll() {
        return facade.getAll();
    }

    @GetMapping("/{roleId}")
    public Mono<RoleResponse> getById(@PathVariable UUID roleId) {
        return facade.getById(roleId);
    }

    @PutMapping("/{roleId}")
    public Mono<RoleResponse> update(@PathVariable UUID roleId, @Valid @RequestBody UpdateRoleRequest request) {
        return facade.update(roleId, request);
    }

    @PatchMapping("/{roleId}/status")
    public Mono<RoleResponse> updateStatus(@PathVariable UUID roleId, @Valid @RequestBody UpdateStatusRequest request) {
        return facade.updateStatus(roleId, request);
    }

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID roleId) {
        return facade.delete(roleId);
    }
}

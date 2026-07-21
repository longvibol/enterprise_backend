package com.pisethjavaschool.accesscontrol.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.accesscontrol.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.dto.ReplaceRolePermissionsRequest;
import com.pisethjavaschool.accesscontrol.dto.RolePermissionResponse;
import com.pisethjavaschool.accesscontrol.facade.RolePermissionFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-control/roles/{roleId}/permissions")
public class RolePermissionController {

    private final RolePermissionFacade facade;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> replace(@PathVariable UUID roleId, @Valid @RequestBody ReplaceRolePermissionsRequest request) {
        return facade.replaceRolePermissions(roleId, request);
    }

    @GetMapping
    public Flux<PermissionResponse> getPermissions(@PathVariable UUID roleId) {
        return facade.getPermissions(roleId);
    }

    @PostMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RolePermissionResponse> add(@PathVariable UUID roleId, @PathVariable UUID permissionId) {
        return facade.addPermission(roleId, permissionId);
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> remove(@PathVariable UUID roleId, @PathVariable UUID permissionId) {
        return facade.removePermission(roleId, permissionId);
    }
}

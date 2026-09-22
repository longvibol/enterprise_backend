package com.pisethjavaschool.accesscontrol.userrole.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.accesscontrol.userrole.dto.AssignRoleByCodeRequest;
import com.pisethjavaschool.accesscontrol.userrole.dto.AssignRoleRequest;
import com.pisethjavaschool.accesscontrol.userrole.dto.UserRoleResponse;
import com.pisethjavaschool.accesscontrol.userrole.facade.UserRoleFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-control/users/{userId}/roles")
public class UserRoleController {

    private final UserRoleFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserRoleResponse> assign(@PathVariable UUID userId, @Valid @RequestBody AssignRoleRequest request) {
        return facade.assignRole(userId, request);
    }
    
    @PostMapping("/by-code")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserRoleResponse> assignByCode(
            @PathVariable UUID userId,
            @Valid @RequestBody AssignRoleByCodeRequest request) {
        return facade.assignRoleByCode(userId, request);
    }

    @GetMapping
    public Flux<UserRoleResponse> getByUser(@PathVariable UUID userId) {
        return facade.getUserRoles(userId);
    }

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> remove(@PathVariable UUID userId, @PathVariable UUID roleId) {
        return facade.removeRole(userId, roleId);
    }
}

package com.pisethjavaschool.accesscontrol.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.dto.AssignRoleRequest;
import com.pisethjavaschool.accesscontrol.dto.UserRoleResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRoleFacade {
    Mono<UserRoleResponse> assignRole(UUID userId, AssignRoleRequest request);
    Flux<UserRoleResponse> getUserRoles(UUID userId);
    Mono<Void> removeRole(UUID userId, UUID roleId);
}

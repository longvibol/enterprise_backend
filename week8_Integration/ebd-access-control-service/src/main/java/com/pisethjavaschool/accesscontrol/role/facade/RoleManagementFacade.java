package com.pisethjavaschool.accesscontrol.role.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.role.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.role.dto.RoleResponse;
import com.pisethjavaschool.accesscontrol.role.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.common.dto.UpdateStatusRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleManagementFacade {
    Mono<RoleResponse> create(CreateRoleRequest request);
    Flux<RoleResponse> getAll();
    Mono<RoleResponse> getById(UUID roleId);
    Mono<RoleResponse> update(UUID roleId, UpdateRoleRequest request);
    Mono<RoleResponse> updateStatus(UUID roleId, UpdateStatusRequest request);
    Mono<Void> delete(UUID roleId);
}

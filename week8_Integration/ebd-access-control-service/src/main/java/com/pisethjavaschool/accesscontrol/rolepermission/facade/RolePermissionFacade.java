package com.pisethjavaschool.accesscontrol.rolepermission.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.permission.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.rolepermission.dto.ReplaceRolePermissionsRequest;
import com.pisethjavaschool.accesscontrol.rolepermission.dto.RolePermissionResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolePermissionFacade {
    Mono<Void> replaceRolePermissions(UUID roleId, ReplaceRolePermissionsRequest request);
    Flux<PermissionResponse> getPermissions(UUID roleId);
    Mono<RolePermissionResponse> addPermission(UUID roleId, UUID permissionId);
    Mono<Void> removePermission(UUID roleId, UUID permissionId);
}

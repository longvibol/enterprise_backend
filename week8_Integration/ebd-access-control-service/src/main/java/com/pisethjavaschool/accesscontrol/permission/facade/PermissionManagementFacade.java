package com.pisethjavaschool.accesscontrol.permission.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.permission.dto.CreatePermissionRequest;
import com.pisethjavaschool.accesscontrol.permission.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.permission.dto.UpdatePermissionRequest;
import com.pisethjavaschool.accesscontrol.common.dto.UpdateStatusRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PermissionManagementFacade {
	Mono<PermissionResponse> create(CreatePermissionRequest request);

	Flux<PermissionResponse> getAll();

	Mono<PermissionResponse> getById(UUID permissionId);

	Mono<PermissionResponse> update(UUID permissionId, UpdatePermissionRequest request);

	Mono<PermissionResponse> updateStatus(UUID permissionId, UpdateStatusRequest request);

	Mono<Void> delete(UUID permissionId);
}

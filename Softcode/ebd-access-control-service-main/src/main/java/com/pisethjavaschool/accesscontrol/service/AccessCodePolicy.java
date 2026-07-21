package com.pisethjavaschool.accesscontrol.service;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface AccessCodePolicy {
    Mono<Void> validateUniqueRoleCode(String code);
    Mono<Void> validateUniqueRoleCode(UUID currentRoleId, String code);
    Mono<Void> validateUniquePermissionCode(String code);
    Mono<Void> validateUniquePermissionCode(UUID currentPermissionId, String code);
}

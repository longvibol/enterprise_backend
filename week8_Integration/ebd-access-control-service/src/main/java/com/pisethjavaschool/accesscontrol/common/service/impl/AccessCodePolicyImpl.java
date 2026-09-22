package com.pisethjavaschool.accesscontrol.common.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.common.exception.DuplicateAccessCodeException;
import com.pisethjavaschool.accesscontrol.permission.repository.AccessPermissionRepository;
import com.pisethjavaschool.accesscontrol.role.repository.AccessRoleRepository;
import com.pisethjavaschool.accesscontrol.common.service.AccessCodePolicy;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccessCodePolicyImpl implements AccessCodePolicy {
    private final AccessRoleRepository roleRepository;
    private final AccessPermissionRepository permissionRepository;

    @Override
    public Mono<Void> validateUniqueRoleCode(String code) {
        String normalizedCode = normalize(code);
        return roleRepository.existsByCode(normalizedCode)
                .flatMap(exists -> exists
                        ? Mono.error(new DuplicateAccessCodeException("Role code already exists: " + normalizedCode))
                        : Mono.empty());
    }

    @Override
    public Mono<Void> validateUniqueRoleCode(UUID currentRoleId, String code) {
        String normalizedCode = normalize(code);
        return roleRepository.findByCode(normalizedCode)
                .filter(role -> !role.getId().equals(currentRoleId))
                .flatMap(role -> Mono.error(new DuplicateAccessCodeException("Role code already exists: " + normalizedCode)))
                .then();
    }

    @Override
    public Mono<Void> validateUniquePermissionCode(String code) {
        String normalizedCode = normalize(code);
        return permissionRepository.existsByCode(normalizedCode)
                .flatMap(exists -> exists
                        ? Mono.error(new DuplicateAccessCodeException("Permission code already exists: " + normalizedCode))
                        : Mono.empty());
    }

    @Override
    public Mono<Void> validateUniquePermissionCode(UUID currentPermissionId, String code) {
        String normalizedCode = normalize(code);
        return permissionRepository.findByCode(normalizedCode)
                .filter(permission -> !permission.getId().equals(currentPermissionId))
                .flatMap(permission -> Mono.error(new DuplicateAccessCodeException("Permission code already exists: " + normalizedCode)))
                .then();
    }

    private String normalize(String code) {
        return code.trim().toUpperCase();
    }
}

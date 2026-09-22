package com.pisethjavaschool.accesscontrol.permission.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;
import com.pisethjavaschool.accesscontrol.permission.repository.AccessPermissionRepository;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PermissionWriterImpl implements PermissionWriter {
    private final AccessPermissionRepository repository;

    @Override
    public Mono<AccessPermission> save(AccessPermission permission) {
        return repository.save(permission);
    }

    @Override
    public Mono<AccessPermission> update(AccessPermission permission) {
        return repository.save(permission);
    }

    @Override
    public Mono<AccessPermission> updateStatus(AccessPermission permission, Boolean active) {
        permission.setActive(active);
        return repository.save(permission);
    }

    @Override
    public Mono<Void> delete(AccessPermission permission) {
        permission.setActive(false);
        return repository.save(permission).then();
    }
}

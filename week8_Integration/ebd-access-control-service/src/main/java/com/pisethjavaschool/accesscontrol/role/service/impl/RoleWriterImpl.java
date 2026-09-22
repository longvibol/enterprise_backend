package com.pisethjavaschool.accesscontrol.role.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.role.repository.AccessRoleRepository;
import com.pisethjavaschool.accesscontrol.role.service.RoleWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleWriterImpl implements RoleWriter {
    private final AccessRoleRepository repository;

    @Override
    public Mono<AccessRole> save(AccessRole role) {
        return repository.save(role);
    }

    @Override
    public Mono<AccessRole> update(AccessRole role) {
        return repository.save(role);
    }

    @Override
    public Mono<AccessRole> updateStatus(AccessRole role, Boolean active) {
        role.setActive(active);
        return repository.save(role);
    }

    @Override
    public Mono<Void> delete(AccessRole role) {
        role.setActive(false);
        return repository.save(role).then();
    }
}

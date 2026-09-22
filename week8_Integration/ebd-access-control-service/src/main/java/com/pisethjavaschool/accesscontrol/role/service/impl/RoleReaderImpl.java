package com.pisethjavaschool.accesscontrol.role.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.common.exception.AccessControlNotFoundException;
import com.pisethjavaschool.accesscontrol.role.repository.AccessRoleRepository;
import com.pisethjavaschool.accesscontrol.role.service.RoleReader;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleReaderImpl implements RoleReader {
    private final AccessRoleRepository repository;

    @Override
    public Mono<AccessRole> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccessControlNotFoundException("Role not found: " + id)));
    }

    @Override
    public Flux<AccessRole> getAll() {
        return repository.findAll();
    }
    
    @Override
    public Mono<AccessRole> getByCode(String code) {
        return repository.findByCode(code)
                .switchIfEmpty(Mono.error(new AccessControlNotFoundException("Role not found: " + code)));
    }
}

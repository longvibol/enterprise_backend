package com.pisethjavaschool.accesscontrol.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.exception.AccessControlNotFoundException;
import com.pisethjavaschool.accesscontrol.repository.AccessRoleRepository;
import com.pisethjavaschool.accesscontrol.service.RoleReader;

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
}

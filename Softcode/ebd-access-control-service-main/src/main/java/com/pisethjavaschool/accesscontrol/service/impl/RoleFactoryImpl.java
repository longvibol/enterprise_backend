package com.pisethjavaschool.accesscontrol.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.service.RoleFactory;

@Service
public class RoleFactoryImpl implements RoleFactory {

    @Override
    public AccessRole create(CreateRoleRequest request) {
        return AccessRole.builder()
                .code(request.code().trim().toUpperCase())
                .name(request.name().trim())
                .description(request.description())
                .roleType(request.roleType())
                .scopeType(request.scopeType())
                .systemRole(false)
                .active(request.active() == null || request.active())
                .build();
    }
}

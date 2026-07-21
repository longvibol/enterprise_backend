package com.pisethjavaschool.accesscontrol.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.service.RoleUpdater;

@Service
public class RoleUpdaterImpl implements RoleUpdater {

    @Override
    public void update(AccessRole role, UpdateRoleRequest request) {
        role.setCode(request.code().trim().toUpperCase());
        role.setName(request.name().trim());
        role.setDescription(request.description());
        role.setRoleType(request.roleType());
        role.setScopeType(request.scopeType());
        role.setActive(request.active() == null || request.active());
    }
}

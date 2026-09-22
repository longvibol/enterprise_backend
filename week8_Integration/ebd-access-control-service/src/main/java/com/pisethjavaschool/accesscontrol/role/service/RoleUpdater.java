package com.pisethjavaschool.accesscontrol.role.service;

import com.pisethjavaschool.accesscontrol.role.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;

public interface RoleUpdater {
    void update(AccessRole role, UpdateRoleRequest request);
}

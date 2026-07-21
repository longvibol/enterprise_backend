package com.pisethjavaschool.accesscontrol.service;

import com.pisethjavaschool.accesscontrol.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessRole;

public interface RoleUpdater {
    void update(AccessRole role, UpdateRoleRequest request);
}

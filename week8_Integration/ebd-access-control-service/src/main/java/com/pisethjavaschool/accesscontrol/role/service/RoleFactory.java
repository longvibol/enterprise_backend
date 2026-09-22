package com.pisethjavaschool.accesscontrol.role.service;

import com.pisethjavaschool.accesscontrol.role.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;

public interface RoleFactory {
    AccessRole create(CreateRoleRequest request);
}

package com.pisethjavaschool.accesscontrol.service;

import com.pisethjavaschool.accesscontrol.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessRole;

public interface RoleFactory {
    AccessRole create(CreateRoleRequest request);
}

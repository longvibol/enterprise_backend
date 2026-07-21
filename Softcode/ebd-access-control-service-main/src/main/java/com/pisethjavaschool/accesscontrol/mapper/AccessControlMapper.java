package com.pisethjavaschool.accesscontrol.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.accesscontrol.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.dto.RolePermissionResponse;
import com.pisethjavaschool.accesscontrol.dto.RoleResponse;
import com.pisethjavaschool.accesscontrol.dto.UserRoleResponse;
import com.pisethjavaschool.accesscontrol.entity.AccessPermission;
import com.pisethjavaschool.accesscontrol.entity.AccessRole;
import com.pisethjavaschool.accesscontrol.entity.RolePermission;
import com.pisethjavaschool.accesscontrol.entity.UserRole;

@Mapper(componentModel = "spring")
public interface AccessControlMapper {
    RoleResponse toRoleResponse(AccessRole role);
    PermissionResponse toPermissionResponse(AccessPermission permission);
    RolePermissionResponse toRolePermissionResponse(RolePermission rolePermission);
    UserRoleResponse toUserRoleResponse(UserRole userRole);
}

package com.pisethjavaschool.accesscontrol.rolepermission.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.accesscontrol.rolepermission.dto.RolePermissionResponse;
import com.pisethjavaschool.accesscontrol.rolepermission.entity.RolePermission;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {
    RolePermissionResponse toResponse(RolePermission rolePermission);
}

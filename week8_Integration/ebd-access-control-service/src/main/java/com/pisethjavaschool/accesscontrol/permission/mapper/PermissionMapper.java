package com.pisethjavaschool.accesscontrol.permission.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.accesscontrol.permission.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toResponse(AccessPermission permission);
}

package com.pisethjavaschool.accesscontrol.role.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.accesscontrol.role.dto.RoleResponse;
import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(AccessRole role);
}

package com.pisethjavaschool.accesscontrol.userrole.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.accesscontrol.userrole.dto.UserRoleResponse;
import com.pisethjavaschool.accesscontrol.userrole.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleResponse toResponse(UserRole userRole);
}

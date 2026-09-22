package com.pisethjavaschool.accesscontrol.role.dto;

import com.pisethjavaschool.accesscontrol.role.enums.RoleType;
import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRoleRequest(
        @NotBlank @Size(max = 100) String code,
        @NotBlank @Size(max = 150) String name,
        @Size(max = 500) String description,
        @NotNull RoleType roleType,
        @NotNull ScopeType scopeType,
        Boolean active
) {}

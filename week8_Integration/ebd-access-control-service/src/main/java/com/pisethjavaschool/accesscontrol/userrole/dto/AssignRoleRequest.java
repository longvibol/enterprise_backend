package com.pisethjavaschool.accesscontrol.userrole.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
        @NotNull UUID roleId,
        @NotNull ScopeType scopeType,
        UUID scopeId
) {}

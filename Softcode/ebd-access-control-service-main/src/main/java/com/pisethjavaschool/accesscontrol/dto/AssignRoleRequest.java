package com.pisethjavaschool.accesscontrol.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.enums.ScopeType;

import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
        @NotNull UUID roleId,
        @NotNull ScopeType scopeType,
        UUID scopeId
) {}

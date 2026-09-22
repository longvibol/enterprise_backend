package com.pisethjavaschool.accesscontrol.userrole.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record AssignRoleByCodeRequest(
        @NotBlank String roleCode,
        UUID scopeId
) {
}
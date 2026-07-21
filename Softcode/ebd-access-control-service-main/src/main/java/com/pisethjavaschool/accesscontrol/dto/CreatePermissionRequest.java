package com.pisethjavaschool.accesscontrol.dto;

import com.pisethjavaschool.accesscontrol.enums.AccessModule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePermissionRequest(
        @NotBlank @Size(max = 100) String code,
        @NotBlank @Size(max = 150) String name,
        @Size(max = 500) String description,
        @NotNull AccessModule module,
        Boolean active
) {}

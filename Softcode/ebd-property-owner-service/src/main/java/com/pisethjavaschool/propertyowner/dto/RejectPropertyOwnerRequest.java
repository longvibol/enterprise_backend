package com.pisethjavaschool.propertyowner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RejectPropertyOwnerRequest(
        @NotBlank @Size(max = 1000) String reason
) {}

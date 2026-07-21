package com.pisethjavaschool.accesscontrol.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull Boolean active
) {}

package com.pisethjavaschool.accesscontrol.common.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull Boolean active
) {}

package com.pisethjavaschool.userservice.authentication.dto;

import com.pisethjavaschool.userservice.user.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "User type is required")
        UserType userType
) {
}

package com.pisethjavaschool.userservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

        @NotBlank(message = "New password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
                message = "Password must contain uppercase, lowercase, and number"
        )
        String newPassword
) {
}

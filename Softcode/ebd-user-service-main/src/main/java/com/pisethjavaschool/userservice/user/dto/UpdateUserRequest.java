package com.pisethjavaschool.userservice.user.dto;

import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UpdateUserRequest(

        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @Pattern(
                regexp = "^\\+[1-9]\\d{0,3}$",
                message = "Country code must be valid, example: +855"
        )
        String countryCode,

        @Pattern(
                regexp = "^\\d{6,15}$",
                message = "Phone number must contain 6 to 15 digits"
        )
        String phoneNumber,

        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        Gender gender,

        UUID photoMediaId,

        AccountStatus accountStatus
) {
}

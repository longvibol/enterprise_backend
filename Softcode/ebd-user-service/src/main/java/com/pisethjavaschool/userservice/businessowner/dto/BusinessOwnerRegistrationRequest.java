package com.pisethjavaschool.userservice.businessowner.dto;

import com.pisethjavaschool.platform.propertyowner.client.dto.OwnerType;
import com.pisethjavaschool.userservice.businessowner.enums.BusinessType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BusinessOwnerRegistrationRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Country code is required")
        @Pattern(
                regexp = "^\\+[1-9]\\d{0,3}$",
                message = "Country code must be valid, example: +855"
        )
        String countryCode,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\d{6,15}$",
                message = "Phone number must contain 6 to 15 digits"
        )
        String phoneNumber,

        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
                message = "Password must contain uppercase, lowercase, and number"
        )
        String password,

        @NotNull(message = "Business type is required")
        BusinessType businessType,
        
        @NotNull(message = "Owner type is required")
        OwnerType ownerType, 

        @Size(max = 150, message = "Business name must not exceed 150 characters")
        String businessName,

        @Size(max = 80, message = "Telegram must not exceed 80 characters")
        String telegram
) {
}

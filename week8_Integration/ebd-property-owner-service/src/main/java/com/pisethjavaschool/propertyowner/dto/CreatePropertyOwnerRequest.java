package com.pisethjavaschool.propertyowner.dto;

import com.pisethjavaschool.propertyowner.enums.OwnerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePropertyOwnerRequest(
        @NotNull OwnerType ownerType,
        @NotBlank @Size(max = 150) String displayName,
        @Size(max = 150) String businessName,
        @Size(max = 80) String businessRegistrationNumber,
        @Size(max = 80) String taxNumber,
        @Size(max = 30) String phoneNumber,
        @Email @Size(max = 150) String email,
        @Size(max = 100) String telegram
) {}

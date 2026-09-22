package com.pisethjavaschool.propertyowner.dto;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.enums.OwnerType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record InternalCreatePropertyOwnerRequest(
        @NotNull UUID userId,
        @NotNull OwnerType ownerType,
        @NotBlank String displayName,
        String businessName,
        String businessRegistrationNumber,
        String taxNumber,
        String phoneNumber,
        String email,
        String telegram) {
    public CreatePropertyOwnerRequest toCreateRequest() {
        return new CreatePropertyOwnerRequest(ownerType, displayName, businessName, businessRegistrationNumber,
                taxNumber, phoneNumber, email, telegram);
    }
}
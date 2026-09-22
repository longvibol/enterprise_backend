package com.pisethjavaschool.platform.propertyowner.client.dto;

import java.util.UUID;

public record CreatePropertyOwnerCommand(
        UUID userId,
        OwnerType ownerType,
        String displayName,
        String businessName,
        String businessRegistrationNumber,
        String taxNumber,
        String phoneNumber,
        String email,
        String telegram) {
}
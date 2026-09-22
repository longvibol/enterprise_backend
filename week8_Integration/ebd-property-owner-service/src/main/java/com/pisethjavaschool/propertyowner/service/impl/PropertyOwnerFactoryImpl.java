package com.pisethjavaschool.propertyowner.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.dto.CreatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerFactory;

@Service
public class PropertyOwnerFactoryImpl implements PropertyOwnerFactory {

    @Override
    public PropertyOwner createDraft(UUID userId, CreatePropertyOwnerRequest request) {
        return PropertyOwner.builder()
                .userId(userId)
                .ownerType(request.ownerType())
                .displayName(request.displayName().trim())
                .businessName(request.businessName())
                .businessRegistrationNumber(request.businessRegistrationNumber())
                .taxNumber(request.taxNumber())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .telegram(request.telegram())
                .verificationStatus(OwnerVerificationStatus.DRAFT)
                .active(true)
                .build();
    }
}

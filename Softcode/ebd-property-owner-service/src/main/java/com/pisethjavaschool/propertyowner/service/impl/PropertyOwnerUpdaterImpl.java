package com.pisethjavaschool.propertyowner.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.dto.UpdatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerUpdater;

@Service
public class PropertyOwnerUpdaterImpl implements PropertyOwnerUpdater {

    @Override
    public PropertyOwner update(PropertyOwner owner, UpdatePropertyOwnerRequest request) {
        owner.setOwnerType(request.ownerType());
        owner.setDisplayName(request.displayName().trim());
        owner.setBusinessName(request.businessName());
        owner.setBusinessRegistrationNumber(request.businessRegistrationNumber());
        owner.setTaxNumber(request.taxNumber());
        owner.setPhoneNumber(request.phoneNumber());
        owner.setEmail(request.email());
        owner.setTelegram(request.telegram());

        if (owner.getVerificationStatus() == OwnerVerificationStatus.REJECTED) {
            owner.setVerificationStatus(OwnerVerificationStatus.DRAFT);
            owner.setRejectionReason(null);
        }

        return owner;
    }
}

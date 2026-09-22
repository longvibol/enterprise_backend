package com.pisethjavaschool.propertyowner.service;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.CreatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

public interface PropertyOwnerFactory {
    PropertyOwner createDraft(UUID userId, CreatePropertyOwnerRequest request);
}

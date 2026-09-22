package com.pisethjavaschool.propertyowner.service;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.CreateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.entity.OwnerDocument;

public interface OwnerDocumentFactory {
    OwnerDocument create(UUID ownerId, CreateOwnerDocumentRequest request);
}

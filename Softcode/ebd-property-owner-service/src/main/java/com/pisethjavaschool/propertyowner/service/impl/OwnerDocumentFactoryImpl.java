package com.pisethjavaschool.propertyowner.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.dto.CreateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.entity.OwnerDocument;
import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentFactory;

@Service
public class OwnerDocumentFactoryImpl implements OwnerDocumentFactory {

    @Override
    public OwnerDocument create(UUID ownerId, CreateOwnerDocumentRequest request) {
        return OwnerDocument.builder()
                .ownerId(ownerId)
                .documentTypeCode(request.documentTypeCode().trim().toUpperCase())
                .documentNumber(request.documentNumber())
                .mediaId(request.mediaId())
                .status(OwnerDocumentStatus.PENDING_REVIEW)
                .active(true)
                .build();
    }
}

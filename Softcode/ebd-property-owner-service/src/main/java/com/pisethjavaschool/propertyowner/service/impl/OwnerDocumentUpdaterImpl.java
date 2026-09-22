package com.pisethjavaschool.propertyowner.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.propertyowner.dto.UpdateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.entity.OwnerDocument;
import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentUpdater;

@Service
public class OwnerDocumentUpdaterImpl implements OwnerDocumentUpdater {

    @Override
    public OwnerDocument update(OwnerDocument document, UpdateOwnerDocumentRequest request) {
        document.setDocumentTypeCode(request.documentTypeCode().trim().toUpperCase());
        document.setDocumentNumber(request.documentNumber());
        document.setMediaId(request.mediaId());
        document.setStatus(OwnerDocumentStatus.PENDING_REVIEW);
        document.setRejectionReason(null);
        return document;
    }
}

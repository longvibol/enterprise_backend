package com.pisethjavaschool.propertyowner.service;

import com.pisethjavaschool.propertyowner.dto.UpdateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.entity.OwnerDocument;

public interface OwnerDocumentUpdater {
    OwnerDocument update(OwnerDocument document, UpdateOwnerDocumentRequest request);
}

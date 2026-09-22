package com.pisethjavaschool.propertyowner.exception;

import com.pisethjavaschool.platform.exception.NotFoundException;

public class OwnerDocumentNotFoundException extends NotFoundException {

    public OwnerDocumentNotFoundException() {
        super(
            PropertyOwnerErrorCode.OWNER_DOCUMENT_NOT_FOUND,
            "Owner document was not found"
        );
    }
}
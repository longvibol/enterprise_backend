package com.pisethjavaschool.propertyowner.exception;

import com.pisethjavaschool.platform.exception.ConflictException;

public class PropertyOwnerAlreadyExistsException extends ConflictException {

    public PropertyOwnerAlreadyExistsException() {
        super(
            PropertyOwnerErrorCode.PROPERTY_OWNER_ALREADY_EXISTS,
            "Property owner profile already exists"
        );
    }
}
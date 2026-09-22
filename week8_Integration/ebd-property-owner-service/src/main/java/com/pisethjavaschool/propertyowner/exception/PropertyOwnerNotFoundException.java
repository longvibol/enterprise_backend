package com.pisethjavaschool.propertyowner.exception;

import com.pisethjavaschool.platform.exception.NotFoundException;

public class PropertyOwnerNotFoundException extends NotFoundException {

    public PropertyOwnerNotFoundException() {
        super(
            PropertyOwnerErrorCode.PROPERTY_OWNER_NOT_FOUND,
            "Property owner profile was not found"
        );
    }
}
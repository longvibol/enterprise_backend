package com.pisethjavaschool.propertyowner.exception;

import com.pisethjavaschool.platform.exception.ForbiddenException;

public class PropertyOwnerAccessDeniedException extends ForbiddenException {

    public PropertyOwnerAccessDeniedException() {
        super(
            PropertyOwnerErrorCode.PROPERTY_OWNER_ACCESS_DENIED,
            "You are not allowed to access this property owner profile"
        );
    }
}
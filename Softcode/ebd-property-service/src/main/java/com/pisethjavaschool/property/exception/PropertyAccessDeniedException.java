package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.ForbiddenException;

public class PropertyAccessDeniedException extends ForbiddenException {
    public PropertyAccessDeniedException(String message) {
        super(PropertyErrorCode.PROPERTY_ACCESS_DENIED, message);
    }
}

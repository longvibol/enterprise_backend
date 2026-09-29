package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.ForbiddenException;

public class PropertyLimitExceededException extends ForbiddenException {

    public PropertyLimitExceededException(String message) {
        super(PropertyErrorCode.PROPERTY_LIMIT_EXCEEDED, message);
    }
}

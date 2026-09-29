package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class InvalidPropertyTypeException extends BadRequestException {
    public InvalidPropertyTypeException(String message) {
        super(PropertyErrorCode.INVALID_PROPERTY_TYPE, message);
    }
}

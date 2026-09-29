package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class InvalidPropertyStatusException extends BadRequestException {
    public InvalidPropertyStatusException(String message) {
        super(PropertyErrorCode.INVALID_PROPERTY_STATUS, message);
    }
}

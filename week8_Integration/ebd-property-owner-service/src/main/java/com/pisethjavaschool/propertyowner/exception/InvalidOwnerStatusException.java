package com.pisethjavaschool.propertyowner.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class InvalidOwnerStatusException extends BadRequestException {

    public InvalidOwnerStatusException(String message) {
        super(PropertyOwnerErrorCode.INVALID_OWNER_STATUS, message);
    }
}
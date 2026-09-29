package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class InvalidAddressException extends BadRequestException {
    public InvalidAddressException(String message) {
        super(PropertyErrorCode.INVALID_ADDRESS, message);
    }
}

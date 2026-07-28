package com.pisethjavaschool.accesscontrol.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class InvalidScopeException extends BadRequestException {
    public InvalidScopeException(String message) {
        super("INVALID_SCOPE", message);
    }
}
package com.pisethjavaschool.accesscontrol.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String errorCode, String message) {
        super(errorCode, message, HttpStatus.FORBIDDEN);
    }
}

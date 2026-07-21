package com.pisethjavaschool.accesscontrol.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String errorCode, String message) {
        super(errorCode, message, HttpStatus.UNAUTHORIZED);
    }
}

package com.pisethjavaschool.userservice.common.exception;

public class NotFoundException extends com.pisethjavaschool.platform.exception.NotFoundException {
    public NotFoundException(String message) {
        super("USER_NOT_FOUND", message);
    }
}

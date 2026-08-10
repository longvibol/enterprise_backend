package com.pisethjavaschool.userservice.common.exception;

import com.pisethjavaschool.platform.exception.ConflictException;

public class DuplicateUserException extends ConflictException {
    public DuplicateUserException(String message) {
        super("USER_ALREADY_EXISTS", message);
    }
}

package com.pisethjavaschool.accesscontrol.common.exception;

import com.pisethjavaschool.platform.exception.ConflictException;

public class DuplicateAccessCodeException extends ConflictException {
    public DuplicateAccessCodeException(String message) {
        super("DUPLICATE_ACCESS_CODE", message);
    }
}

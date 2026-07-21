package com.pisethjavaschool.accesscontrol.exception;

import com.pisethjavaschool.accesscontrol.common.exception.ConflictException;

public class DuplicateAccessCodeException extends ConflictException {
    public DuplicateAccessCodeException(String message) {
        super("DUPLICATE_ACCESS_CODE", message);
    }
}

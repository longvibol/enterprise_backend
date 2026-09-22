package com.pisethjavaschool.accesscontrol.common.exception;

import com.pisethjavaschool.platform.exception.NotFoundException;

public class AccessControlNotFoundException extends NotFoundException {
    public AccessControlNotFoundException(String message) {
        super("ACCESS_CONTROL_NOT_FOUND", message);
    }
}

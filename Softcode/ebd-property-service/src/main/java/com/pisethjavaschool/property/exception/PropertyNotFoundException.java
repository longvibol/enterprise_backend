package com.pisethjavaschool.property.exception;

import com.pisethjavaschool.platform.exception.NotFoundException;

public class PropertyNotFoundException extends NotFoundException {
    public PropertyNotFoundException(String message) {
        super(PropertyErrorCode.PROPERTY_NOT_FOUND, message);
    }
}

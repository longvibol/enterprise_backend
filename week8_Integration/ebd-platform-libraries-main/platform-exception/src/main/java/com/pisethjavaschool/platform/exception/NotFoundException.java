package com.pisethjavaschool.platform.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
	public NotFoundException(String message) {
		super("RESOURCE_NOT_FOUND", message, HttpStatus.NOT_FOUND);
	}

	public NotFoundException(String errorCode, String message) {
		super(errorCode, message, HttpStatus.NOT_FOUND);
	}
}

package com.pisethjavaschool.platform.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {
	public BadRequestException(String errorCode, String message) {
		super(errorCode, message, HttpStatus.BAD_REQUEST);
	}
}

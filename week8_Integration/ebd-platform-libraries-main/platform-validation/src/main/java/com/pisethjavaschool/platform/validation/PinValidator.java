package com.pisethjavaschool.platform.validation;

public final class PinValidator {
	private PinValidator() {
	}

	public static boolean isValidPin(String pin) {
		return pin != null && pin.matches("^[0-9]{4,6}$");
	}
}

package com.pisethjavaschool.platform.validation;

public final class PhoneNumberValidator {
	private PhoneNumberValidator() {
	}

	public static boolean isValidCambodianPhone(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isBlank())
			return false;
		String normalized = phoneNumber.replaceAll("\\s+", "");
		return normalized.matches("^(0|855)?[1-9][0-9]{7,8}$");
	}
}

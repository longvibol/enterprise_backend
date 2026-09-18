package com.pisethjavaschool.platform.validation;

public final class SlugValidator {
	private SlugValidator() {
	}

	public static boolean isValidSlug(String slug) {
		return slug != null && slug.matches("^[a-z0-9]+(?:-[a-z0-9]+)*$");
	}
}

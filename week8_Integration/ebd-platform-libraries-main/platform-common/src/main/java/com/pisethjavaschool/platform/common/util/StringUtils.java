package com.pisethjavaschool.platform.common.util;

public final class StringUtils {
	private StringUtils() {
	}

	public static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static String trimToNull(String value) {
		if (!hasText(value))
			return null;
		return value.trim();
	}
}

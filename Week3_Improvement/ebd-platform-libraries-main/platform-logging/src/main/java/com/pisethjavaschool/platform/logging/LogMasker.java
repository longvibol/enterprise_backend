package com.pisethjavaschool.platform.logging;

public final class LogMasker {
	private LogMasker() {
	}

	public static String maskPhone(String phone) {
		if (phone == null || phone.length() < 4)
			return "****";
		return "****" + phone.substring(phone.length() - 4);
	}

	public static String maskEmail(String email) {
		if (email == null || !email.contains("@"))
			return "****";
		String[] p = email.split("@", 2);
		String n = p[0];
		return n.length() <= 2 ? "**@" + p[1] : n.charAt(0) + "***" + n.charAt(n.length() - 1) + "@" + p[1];
	}

	public static String maskToken(String token) {
		if (token == null || token.length() < 10)
			return "****";
		return token.substring(0, 4) + "****" + token.substring(token.length() - 4);
	}
}

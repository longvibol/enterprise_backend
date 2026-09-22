package com.pisethjavaschool.platform.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateTimeUtils {
	private DateTimeUtils() {
	}

	public static ZonedDateTime toZone(Instant instant, String zoneId) {
		return instant == null ? null : instant.atZone(ZoneId.of(zoneId));
	}
}

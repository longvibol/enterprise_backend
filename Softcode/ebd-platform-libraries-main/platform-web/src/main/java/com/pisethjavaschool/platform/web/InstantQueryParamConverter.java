package com.pisethjavaschool.platform.web;

import org.springframework.core.convert.converter.Converter;
import java.time.Instant;

public class InstantQueryParamConverter implements Converter<String, Instant> {
	@Override
	public Instant convert(String source) {
		return source == null || source.isBlank() ? null : Instant.parse(source);
	}
}

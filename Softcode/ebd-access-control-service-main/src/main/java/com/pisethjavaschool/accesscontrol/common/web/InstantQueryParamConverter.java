package com.pisethjavaschool.accesscontrol.common.web;

import java.time.Instant;

import org.springframework.core.convert.converter.Converter;

public class InstantQueryParamConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(String source) {
        return source == null || source.isBlank() ? null : Instant.parse(source);
    }
}

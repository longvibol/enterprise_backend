package com.pisethjavaschool.platform.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class PlatformWebFluxConversionConfig implements WebFluxConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new InstantQueryParamConverter());
	}
}

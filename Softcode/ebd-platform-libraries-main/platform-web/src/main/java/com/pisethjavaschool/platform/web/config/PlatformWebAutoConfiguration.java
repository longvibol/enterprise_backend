package com.pisethjavaschool.platform.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import com.pisethjavaschool.platform.web.PlatformWebFluxConversionConfig;
import com.pisethjavaschool.platform.web.RequestIdWebFilter;

@AutoConfiguration
@Import({PlatformWebFluxConversionConfig.class, RequestIdWebFilter.class})
public class PlatformWebAutoConfiguration {}
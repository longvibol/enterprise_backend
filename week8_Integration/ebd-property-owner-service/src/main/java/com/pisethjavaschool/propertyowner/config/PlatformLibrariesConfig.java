package com.pisethjavaschool.propertyowner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pisethjavaschool.platform.exception.GlobalExceptionHandler;
import com.pisethjavaschool.platform.openapi.PlatformOpenApiConfig;
import com.pisethjavaschool.platform.r2dbc.PlatformReactiveTransactionManagementConfig;
import com.pisethjavaschool.platform.r2dbc.config.PlatformR2dbcAuditingAutoConfiguration;
import com.pisethjavaschool.platform.security.audit.SecurityCurrentAuditorProvider;
import com.pisethjavaschool.platform.security.config.CurrentUserReaderConfiguration;
import com.pisethjavaschool.platform.user.client.config.PlatformUserClientConfiguration;
import com.pisethjavaschool.platform.web.PlatformWebFluxConversionConfig;
import com.pisethjavaschool.platform.web.RequestIdWebFilter;

@Configuration
@Import({
        GlobalExceptionHandler.class,
        PlatformOpenApiConfig.class,
        PlatformReactiveTransactionManagementConfig.class,
        PlatformR2dbcAuditingAutoConfiguration.class,
        SecurityCurrentAuditorProvider.class,
        PlatformUserClientConfiguration.class, //
        CurrentUserReaderConfiguration.class,
        PlatformWebFluxConversionConfig.class,
        RequestIdWebFilter.class
})
public class PlatformLibrariesConfig {
}

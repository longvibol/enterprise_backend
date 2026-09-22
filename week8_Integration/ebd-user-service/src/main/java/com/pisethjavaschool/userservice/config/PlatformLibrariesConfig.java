package com.pisethjavaschool.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pisethjavaschool.platform.accesscontrol.client.config.AccessControlClientConfiguration;
import com.pisethjavaschool.platform.exception.GlobalExceptionHandler;
import com.pisethjavaschool.platform.openapi.PlatformOpenApiConfig;
import com.pisethjavaschool.platform.propertyowner.client.config.PropertyOwnerClientConfiguration;
import com.pisethjavaschool.platform.r2dbc.PlatformReactiveTransactionManagementConfig;
import com.pisethjavaschool.platform.security.audit.SecurityCurrentAuditorProvider;
import com.pisethjavaschool.platform.security.config.CurrentUserReaderConfiguration;
import com.pisethjavaschool.platform.web.PlatformWebFluxConversionConfig;
import com.pisethjavaschool.platform.web.RequestIdWebFilter;

@Configuration
@Import({
        GlobalExceptionHandler.class,
        PlatformOpenApiConfig.class,
        AccessControlClientConfiguration.class,
        PlatformReactiveTransactionManagementConfig.class,
        SecurityCurrentAuditorProvider.class,
        CurrentUserReaderConfiguration.class,
        PlatformWebFluxConversionConfig.class,
        PropertyOwnerClientConfiguration.class,
        RequestIdWebFilter.class
})
public class PlatformLibrariesConfig {
}

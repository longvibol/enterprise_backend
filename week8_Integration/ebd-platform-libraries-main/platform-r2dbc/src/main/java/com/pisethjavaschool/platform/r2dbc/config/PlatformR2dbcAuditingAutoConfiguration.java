package com.pisethjavaschool.platform.r2dbc.config;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;

import reactor.core.publisher.Mono;

@AutoConfiguration
@EnableR2dbcAuditing
public class PlatformR2dbcAuditingAutoConfiguration {

    @Bean
    public ReactiveAuditorAware<UUID> reactiveAuditorAware(
            ObjectProvider<CurrentAuditorProvider> provider
    ) {

        return () -> {
            CurrentAuditorProvider auditorProvider = provider.getIfAvailable();

            if (auditorProvider == null) {
                return Mono.empty();
            }

            return auditorProvider.getCurrentAuditorId();
        };
    }

}
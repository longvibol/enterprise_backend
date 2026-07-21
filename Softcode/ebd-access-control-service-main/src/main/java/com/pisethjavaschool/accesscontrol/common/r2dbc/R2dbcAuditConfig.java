package com.pisethjavaschool.accesscontrol.common.r2dbc;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import com.pisethjavaschool.accesscontrol.common.audit.CurrentAuditorProvider;

import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcAuditing
public class R2dbcAuditConfig {

    @Bean
    public ReactiveAuditorAware<UUID> reactiveAuditorAware(ObjectProvider<CurrentAuditorProvider> provider) {
        return () -> {
            CurrentAuditorProvider auditorProvider = provider.getIfAvailable();
            if (auditorProvider == null) {
                return Mono.empty();
            }
            return auditorProvider.getCurrentAuditorId();
        };
    }
}

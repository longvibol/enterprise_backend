package com.pisethjavaschool.platform.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;
import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.platform.security.PlatformUserIdResolver;
import com.pisethjavaschool.platform.security.audit.SecurityCurrentAuditorProvider;

@AutoConfiguration(afterName = "com.pisethjavaschool.platform.user.client.config.PlatformUserClientConfiguration")
public class CurrentUserReaderConfiguration {
	/*
    @Bean
    public CurrentUserReader currentUserReader(CurrentAuditorProvider currentAuditorProvider) {
        return new CurrentUserReader(currentAuditorProvider);
    }
    */
	
	@Bean
    @ConditionalOnBean(PlatformUserIdResolver.class)
    @ConditionalOnMissingBean(CurrentAuditorProvider.class)
    public CurrentAuditorProvider securityCurrentAuditorProvider(PlatformUserIdResolver resolver) {
        return new SecurityCurrentAuditorProvider(resolver);
    }


    @Bean
    @ConditionalOnMissingBean // NEW
    public CurrentUserReader currentUserReader(CurrentAuditorProvider currentAuditorProvider) {
        return new CurrentUserReader(currentAuditorProvider);
    } 
}
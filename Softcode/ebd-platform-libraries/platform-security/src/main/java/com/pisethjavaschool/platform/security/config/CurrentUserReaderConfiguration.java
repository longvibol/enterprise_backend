package com.pisethjavaschool.platform.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;
import com.pisethjavaschool.platform.security.CurrentUserReader;

@Configuration
public class CurrentUserReaderConfiguration {

    @Bean
    public CurrentUserReader currentUserReader(CurrentAuditorProvider currentAuditorProvider) {
        return new CurrentUserReader(currentAuditorProvider);
    } 
}
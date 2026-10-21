package com.pisethjavaschool.platform.user.client.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import com.pisethjavaschool.platform.user.client.PlatformUserClient;
import com.pisethjavaschool.platform.user.client.impl.DefaultPlatformUserClient;
@Configuration
@EnableConfigurationProperties(PlatformUserClientProperties.class)
public class PlatformUserClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PlatformUserClient platformUserClient(
            ObjectProvider<WebClient.Builder> webClientBuilderProvider,
            PlatformUserClientProperties properties) {

        WebClient.Builder builder = webClientBuilderProvider.getIfAvailable(WebClient::builder);
        return new DefaultPlatformUserClient(builder, properties);
    }
}
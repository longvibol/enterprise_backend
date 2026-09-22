package com.pisethjavaschool.platform.user.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.clients.user")
public class PlatformUserClientProperties {
    private String baseUrl = "http://localhost:8089";
    private String rootPath = "/api/v1/users";
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getRootPath() { return rootPath; }
    public void setRootPath(String rootPath) { this.rootPath = rootPath; }
}
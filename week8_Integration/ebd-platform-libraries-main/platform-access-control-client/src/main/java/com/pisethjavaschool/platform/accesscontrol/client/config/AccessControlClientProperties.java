package com.pisethjavaschool.platform.accesscontrol.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.clients.access-control")
public class AccessControlClientProperties {
	private String baseUrl = "http://localhost:8091";
	private String rootPath = "/api/v1/access-control";

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
}
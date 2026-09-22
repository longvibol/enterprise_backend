package com.pisethjavaschool.platform.propertyowner.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.clients.property-owner")
public class PropertyOwnerClientProperties {
	private String baseUrl = "http://localhost:8069";
	private String rootPath = "/api/v1/property-owners";

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
package com.pisethjavaschool.platform.accesscontrol.client;

import java.util.UUID;

import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;

import reactor.core.publisher.Mono;

public interface AccessControlClient {
	Mono<Void> assignRole(UUID userId, AccessRoleCode roleCode, UUID scopeId);
	
	Mono<Boolean> hasPermission(UUID userId, String permissionCode, AccessScopeType scopeType, UUID scopeId); 
}
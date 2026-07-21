package com.pisethjavaschool.accesscontrol.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.accesscontrol.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.dto.PermissionCheckRequest;
import com.pisethjavaschool.accesscontrol.dto.PermissionCheckResponse;
import com.pisethjavaschool.accesscontrol.facade.GetAuthorityFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-control")
public class AuthorityController {

    private final GetAuthorityFacade facade;

    @GetMapping("/users/{userId}/authorities")
    public Mono<AuthorityResponse> getByUserId(@PathVariable UUID userId) {
        return facade.getByUserId(userId);
    }

    @GetMapping("/me/authorities")
    public Mono<AuthorityResponse> getMe() {
        return facade.getMe();
    }

    @PostMapping("/check-permission")
    public Mono<PermissionCheckResponse> check(@Valid @RequestBody PermissionCheckRequest request) {
        return facade.check(request);
    }
}

package com.pisethjavaschool.userservice.authentication.service.impl;

import org.springframework.stereotype.Service;
import com.pisethjavaschool.userservice.authentication.dto.LoginRequest;
import com.pisethjavaschool.userservice.authentication.dto.LoginResponse;
import com.pisethjavaschool.userservice.authentication.dto.RefreshTokenRequest;
import com.pisethjavaschool.userservice.common.exception.NotFoundException;
import com.pisethjavaschool.userservice.user.repository.UserAccountRepository;
import com.pisethjavaschool.userservice.authentication.service.AuthService;
import com.pisethjavaschool.userservice.user.service.UserQueryService;
import com.pisethjavaschool.userservice.authentication.keycloak.KeycloakUserClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final KeycloakUserClient keycloakUserClient;
    private final UserAccountRepository accountRepository;
    private final UserQueryService userQueryService;

    @Override
    public Mono<LoginResponse> login(LoginRequest request) {
        String username = request.username().trim().toLowerCase();
        return accountRepository.findByUsernameIgnoreCaseAndUserType(username, request.userType())
                .switchIfEmpty(Mono.error(new NotFoundException("Invalid username or password.")))
                .flatMap(account -> keycloakUserClient.login(username, request.password())
                        .zipWith(userQueryService.findById(account.getId()))
                        .map(tuple -> new LoginResponse(tuple.getT1().accessToken(), tuple.getT1().refreshToken(), tuple.getT1().expiresIn(), tuple.getT1().tokenType(), tuple.getT2())));
    }
    
    @Override
    public Mono<LoginResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken().trim();

        return keycloakUserClient.refreshToken(refreshToken)
                .map(token -> new LoginResponse(
                        token.accessToken(),
                        token.refreshToken(),
                        token.expiresIn(),
                        token.tokenType(),
                        null
                ));
    }
}

package com.pisethjavaschool.userservice.user.service.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.entity.UserProfile;
import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.RegistrationStatus;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UpdateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.common.exception.DuplicateUserException;
import com.pisethjavaschool.userservice.common.exception.NotFoundException;
import com.pisethjavaschool.userservice.user.mapper.UserMapper;
import com.pisethjavaschool.userservice.user.repository.UserAccountRepository;
import com.pisethjavaschool.userservice.user.repository.UserProfileRepository;
import com.pisethjavaschool.userservice.user.service.UserCommandService;
import com.pisethjavaschool.userservice.authentication.keycloak.KeycloakUserClient;
import com.pisethjavaschool.userservice.common.service.PasswordPolicy;
import com.pisethjavaschool.userservice.common.service.UsernameResolver;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserAccountRepository accountRepository;
    private final UserProfileRepository profileRepository;
    private final KeycloakUserClient keycloakUserClient;
    private final UserMapper mapper;
    private final UsernameResolver usernameResolver;
    private final PasswordPolicy passwordPolicy;

    @Override
    @Transactional
    public Mono<UserResponse> create(CreateUserRequest request) {
        String username = usernameResolver.resolve(request.email(), request.countryCode(), request.phoneNumber());
        return passwordPolicy.validate(request.password())
                .then(validateUniqueUsername(username))
                .then(createIdentity(request, username))
                .flatMap(account -> createProfile(account, request)
                        .map(profile -> mapper.toResponse(account, profile)));
    }

    @Override
    @Transactional
    public Mono<UserResponse> update(UUID id, UpdateUserRequest request) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .flatMap(account -> updateAccount(account, request))
                .flatMap(account -> updateProfile(account, request));
    }

    @Override
    @Transactional
    public Mono<Void> delete(UUID id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .flatMap(account -> {
                    account.setAccountStatus(AccountStatus.DELETED);
                    return keycloakUserClient.disableUser(account.getKeycloakUserId())
                            .then(accountRepository.save(account))
                            .then();
                });
    }

    @Override
    public Mono<UserResponse> activate(UUID id) {
        return changeStatus(id, AccountStatus.ACTIVE);
    }

    @Override
    public Mono<UserResponse> deactivate(UUID id) {
        return changeStatus(id, AccountStatus.INACTIVE);
    }

    @Override
    public Mono<Void> resetPassword(UUID id, String newPassword) {
        return passwordPolicy.validate(newPassword)
                .then(accountRepository.findById(id))
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .flatMap(account -> keycloakUserClient.resetPassword(account.getKeycloakUserId(), newPassword));
    }

    private Mono<UserAccount> updateAccount(UserAccount account, UpdateUserRequest request) {
        String username = usernameResolver.resolve(request.email(), request.countryCode(), request.phoneNumber());
        mapper.updateAccount(request, username, account);
        boolean enabled = account.getAccountStatus() == AccountStatus.ACTIVE;
        return keycloakUserClient.updateUser(account.getKeycloakUserId(), account.getEmail(), request.firstName(), request.lastName(), enabled)
                .then(accountRepository.save(account));
    }

    private Mono<UserResponse> updateProfile(UserAccount account, UpdateUserRequest request) {
        return profileRepository.findByUserAccountId(account.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("User profile not found.")))
                .flatMap(profile -> {
                    mapper.updateProfile(request, profile);
                    return profileRepository.save(profile);
                })
                .map(profile -> mapper.toResponse(account, profile));
    }

    private Mono<UserResponse> changeStatus(UUID id, AccountStatus status) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .flatMap(account -> {
                    account.setAccountStatus(status);
                    boolean enabled = status == AccountStatus.ACTIVE;
                    return keycloakUserClient.updateUser(account.getKeycloakUserId(), account.getEmail(), null, null, enabled)
                            .then(accountRepository.save(account));
                })
                .flatMap(account -> profileRepository.findByUserAccountId(account.getId())
                        .map(profile -> mapper.toResponse(account, profile)));
    }

    private Mono<UserAccount> createIdentity(CreateUserRequest request, String username) {
        return keycloakUserClient.createUser(username, request.email(), request.firstName(), request.lastName(), request.password(), true)
                .flatMap(keycloakUserId -> {
                    UserAccount account = mapper.toAccount(
                            request,
                            username,
                            keycloakUserId,
                            AccountStatus.ACTIVE,
                            RegistrationStatus.COMPLETED
                    );
                    return accountRepository.save(account);
                });
    }

    private Mono<UserProfile> createProfile(UserAccount account, CreateUserRequest request) {
        UserProfile profile = mapper.toProfile(request, account.getId());
        return profileRepository.save(profile);
    }

    private Mono<Void> validateUniqueUsername(String username) {
        return accountRepository.existsByUsernameIgnoreCase(username)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateUserException("User already exists."));
                    }
                    return Mono.empty();
                });
    }
}

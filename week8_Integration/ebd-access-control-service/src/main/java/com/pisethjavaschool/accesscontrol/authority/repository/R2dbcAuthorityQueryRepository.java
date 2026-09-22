package com.pisethjavaschool.accesscontrol.authority.repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.pisethjavaschool.accesscontrol.authority.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.authority.dto.AuthorityScopeResponse;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckRequest;
import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcAuthorityQueryRepository implements AuthorityQueryRepository {
    private final DatabaseClient databaseClient;

    @Override
    public Mono<AuthorityResponse> getAuthorities(UUID userId) {
        return databaseClient.sql("""
                SELECT r.code AS role_code,
                       p.code AS permission_code,
                       ur.scope_type AS scope_type,
                       ur.scope_id AS scope_id
                  FROM user_role ur
                  JOIN access_role r ON r.id = ur.role_id AND r.active = true
                  LEFT JOIN role_permission rp ON rp.role_id = r.id
                  LEFT JOIN access_permission p ON p.id = rp.permission_id AND p.active = true
                 WHERE ur.user_id = :userId
                   AND ur.active = true
                 ORDER BY r.code, p.code
                """)
                .bind("userId", userId)
                .map((row, metadata) -> new AuthorityRow(
                        row.get("role_code", String.class),
                        row.get("permission_code", String.class),
                        ScopeType.valueOf(row.get("scope_type", String.class)),
                        row.get("scope_id", UUID.class)
                ))
                .all()
                .collectList()
                .map(rows -> toAuthorityResponse(userId, rows));
    }

    @Override
    public Mono<Boolean> hasPermission(PermissionCheckRequest request) {
        return getAuthorities(request.userId())
                .map(authorities -> authorities.permissions().contains(request.permissionCode().trim().toUpperCase())
                        && hasScope(authorities.scopes(), request));
    }

    private boolean hasScope(List<AuthorityScopeResponse> scopes, PermissionCheckRequest request) {
        if (request.scopeType() == null) {
            return true;
        }

        return scopes.stream().anyMatch(scope ->
                scope.scopeType() == ScopeType.GLOBAL
                        || (scope.scopeType() == request.scopeType()
                        && java.util.Objects.equals(scope.scopeId(), request.scopeId()))
        );
    }

    private AuthorityResponse toAuthorityResponse(UUID userId, List<AuthorityRow> rows) {
        var roles = new LinkedHashSet<String>();
        var permissions = new LinkedHashSet<String>();
        var scopes = rows.stream()
                .map(row -> new AuthorityScopeResponse(row.scopeType(), row.scopeId()))
                .distinct()
                .toList();

        rows.forEach(row -> {
            roles.add(row.roleCode());
            if (row.permissionCode() != null) {
                permissions.add(row.permissionCode());
            }
        });

        return new AuthorityResponse(userId, roles, permissions, scopes);
    }

    private record AuthorityRow(
            String roleCode,
            String permissionCode,
            ScopeType scopeType,
            UUID scopeId
    ) {}
}

package com.pisethjavaschool.platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/** Preserves scope authorities and maps only the explicitly granted realm provisioning role. */
public final class ProvisioningJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter scopes = new JwtGrantedAuthoritiesConverter();
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        var authorities = new ArrayList<GrantedAuthority>(scopes.convert(jwt));
        Object realm = jwt.getClaim("realm_access");
        if (realm instanceof Map<?, ?> claims && claims.get("roles") instanceof Collection<?> roles
                && roles.contains("INTERNAL_PROVISIONING")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_INTERNAL_PROVISIONING"));
        }
        return authorities;
    }
}

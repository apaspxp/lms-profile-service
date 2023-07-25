package com.lms.profileservice.utility;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> claims = (Map<String, Object>) source.getClaims().get("realm_access");
        System.out.println(claims.get("roles"));
        Collection<GrantedAuthority> grantedAuthorities = ((List<String>)claims.get("roles"))
                .stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return grantedAuthorities;
    }
}
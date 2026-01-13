package com.example.rbac.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MobilePassAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public MobilePassAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    public MobilePassAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return "MobileUser";
    }
}

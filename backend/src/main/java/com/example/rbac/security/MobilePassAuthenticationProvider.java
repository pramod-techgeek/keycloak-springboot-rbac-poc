package com.example.rbac.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MobilePassAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        String token = (String) authentication.getCredentials();

        // MOCK VALIDATION
        if ("secret-token".equals(token)) {
            return new MobilePassAuthenticationToken(token,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } else if ("admin-token".equals(token)) {
            return new MobilePassAuthenticationToken(token,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // Return null to allow other providers to try, or throw if we are sure this was
        // meant for us
        // Here we throw because we found the token but it was invalid
        throw new BadCredentialsException("Invalid Mobile-Pass Token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePassAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

package com.example.rbac.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MobilePassAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public MobilePassAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("X-Mobile-Pass");

        if (token != null) {
            try {
                Authentication authRequest = new MobilePassAuthenticationToken(token);
                Authentication authResult = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authResult);
            } catch (AuthenticationException e) {
                // Fail quietly or handle error. For now, continue chain.
                // If authentication is required, downstream security filter will catch it.
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}

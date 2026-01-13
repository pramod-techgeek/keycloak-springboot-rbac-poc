package com.example.rbac.config;

import com.example.rbac.security.JwtRoleConverter;
import com.example.rbac.security.MobilePassAuthenticationFilter;
import com.example.rbac.security.MobilePassAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final MobilePassAuthenticationProvider mobilePassAuthenticationProvider;

    public SecurityConfig(MobilePassAuthenticationProvider mobilePassAuthenticationProvider) {
        this.mobilePassAuthenticationProvider = mobilePassAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(mobilePassAuthenticationProvider);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain mobileSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        http
                .securityMatcher("/mobile/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/mobile/api/public").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new MobilePassAuthenticationFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());

        http
                .securityMatcher("/web/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/web/api/public").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(java.util.Collections.singletonList("*"));
        configuration.setAllowedHeaders(java.util.Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

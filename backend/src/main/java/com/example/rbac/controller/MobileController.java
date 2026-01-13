package com.example.rbac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mobile/api")
public class MobileController {

    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.ok(Map.of("message", "This is a public MOBILE endpoint"));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "This is the MOBILE USER endpoint",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "This is the MOBILE ADMIN endpoint",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()));
    }
}

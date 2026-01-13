package com.example.rbac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.ok(Map.of("message", "This is a public endpoint"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> userEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "This is the USER endpoint",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "This is the ADMIN endpoint",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()));
    }
}

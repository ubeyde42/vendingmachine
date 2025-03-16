package com.ubeyde.sample.controller;

import com.ubeyde.sample.auth.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Operations", description = "Operations for login and authorization")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "This endpoint is used for generating token to be used in admin operations")
    public String authenticateAdmin(@Validated @RequestParam @NotEmpty String adminPassword) {
        return jwtTokenProvider.generateToken(adminPassword);
    }
}

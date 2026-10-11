package com.travel.controller;

import com.travel.entity.User;
import com.travel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) { return service.register(user); }

    @GetMapping("/me")
    public User me(Authentication auth) { return service.me(auth.getName()); }
}

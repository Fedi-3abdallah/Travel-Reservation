package com.travel.service;

import com.travel.entity.Role;
import com.travel.entity.User;
import com.travel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthService(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }

    public User register(User u) {
        if (users.existsByEmail(u.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        u.setId(null);
        u.setRole(Role.USER); // on ne fait jamais confiance au rôle envoyé par le client
        u.setPassword(encoder.encode(u.getPassword()));
        return users.save(u);
    }

    public User me(String email) {
        return users.findByEmail(email).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole().name()).build();
    }
}

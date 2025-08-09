package com.hireit.service;

import com.hireit.domain.User;
import com.hireit.dto.RegisterRequest;
import com.hireit.repository.UserRepository;
import com.hireit.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<User> register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) return Optional.empty();
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRoles(req.getRoles() == null ? Collections.singleton("USER") : req.getRoles());
        userRepository.save(u);
        return Optional.of(u);
    }

    public String generateTokenForUser(User u) {
        Set<String> roles = u.getRoles();
        return jwtUtil.generateToken(u.getEmail(), roles);
    }
}

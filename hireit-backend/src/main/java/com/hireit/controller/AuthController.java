package com.hireit.controller;

import com.hireit.domain.User;
import com.hireit.dto.AuthRequest;
import com.hireit.dto.AuthResponse;
import com.hireit.dto.RegisterRequest;
import com.hireit.service.AuthService;
import com.hireit.repository.UserRepository;
import com.hireit.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        Optional<User> saved = authService.register(req);
        if (saved.isEmpty()) return ResponseEntity.badRequest().body("Email already in use");
        String token = authService.generateTokenForUser(saved.get());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            User u = userRepository.findByEmail(req.getEmail()).get();
            String token = authService.generateTokenForUser(u);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

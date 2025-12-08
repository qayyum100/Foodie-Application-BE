package com.org.Auth_Service.service;

import com.org.Auth_Service.dto.AuthResponse;
import com.org.Auth_Service.dto.LoginRequest;
import com.org.Auth_Service.dto.RegisterRequest;
import com.org.Auth_Service.model.User;
import com.org.Auth_Service.repository.UserRepository;
import com.org.Auth_Service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository repo, JwtUtil jwtUtil) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());

        repo.save(u);

        String token = jwtUtil.generateToken(u.getId(), u.getEmail(), u.getRole());

        return new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
    public AuthResponse login(LoginRequest req) {
        User u = repo.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(u.getId(), u.getEmail(), u.getRole());

        return new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}

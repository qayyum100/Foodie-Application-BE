package com.org.Auth_Service.controller;
import com.org.Auth_Service.dto.AuthResponse;
import com.org.Auth_Service.dto.LoginRequest;
import com.org.Auth_Service.dto.RegisterRequest;
import com.org.Auth_Service.model.User;
import com.org.Auth_Service.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            AuthResponse response = service.register(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            AuthResponse res = service.login(req);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

//    @GetMapping("/me")
//    public ResponseEntity<?> me(HttpServletRequest req) {
//        return ResponseEntity.ok(req.getAttribute("claims"));
//    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest req) {
        Claims claims = (Claims) req.getAttribute("claims"); // still works
        // or from SecurityContext:
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(claims);
    }

}

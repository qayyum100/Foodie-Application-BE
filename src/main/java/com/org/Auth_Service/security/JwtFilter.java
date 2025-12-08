package com.org.Auth_Service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Skip auth endpoints (optional but useful)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.extractClaims(token);

                // put claims on request if you still want to use request.getAttribute("claims")
                req.setAttribute("claims", claims);

                // build Authentication object from claims (role, email, id, etc.)
                String email = claims.get("email", String.class);
                String role = claims.get("role", String.class);

                // create authorities (adjust prefix if you use ROLE_ convention)
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));

                // set into SecurityContext -> Spring will treat request as authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ex) {
                // invalid token -> ensure no auth left in context
                SecurityContextHolder.clearContext();
                // (optionally) you can send 401 here, but usually better to let security handle it.
            }
        }

        chain.doFilter(req, res);
    }
}

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
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Skip auth endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register") || path.startsWith("/api/rating/") ||  path.startsWith("/api/review/");
    }

    //",
    //                        "/api/rating/avg/**",
    //                        "/api/review/count/**"
     //

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.extractClaims(token);

                // optionally keep claims on request
                req.setAttribute("claims", claims);

                String email = claims.get("email", String.class);
//                String role = claims.get("role", String.class); // may be null

                // build authorities robustly
//                List<SimpleGrantedAuthority> authorities;
//                if (role != null && !role.isBlank()) {
//                    // if you later use ROLE_ convention you may add prefix: "ROLE_" + role
//                    authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
//                } else {
//                    authorities = Collections.emptyList();
//                }

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ex) {
                // token invalid -> clear context and proceed (request will be blocked by security)
                SecurityContextHolder.clearContext();
                // optional: log ex for debugging
                // logger.warn("JWT invalid: " + ex.getMessage());
            }
        }

        chain.doFilter(req, res);
    }
}

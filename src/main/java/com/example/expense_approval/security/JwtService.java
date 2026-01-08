package com.example.expense_approval.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key;
    private final long expiry;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiry}") long expiry
    ) {
        this.expiry = expiry;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

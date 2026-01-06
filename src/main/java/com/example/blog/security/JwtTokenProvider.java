package com.example.blog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(System.getenv().getOrDefault("JWT_SECRET_BASE64", "LkH9pQ6nXyV2wZ8rT4mF7cB1aN3jK5sP8qR0tU2vW4xY6zA9bC1dE3fG5hJ7kM9oLpQ2rS4tU6vW8xY0")));
    private final long validityInMs = 1000L * 60 * 60 * 24; // 24h

    public String createToken(String username, Set<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMs);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        return (List<String>) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("roles", List.class);
    }
}


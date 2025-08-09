package com.hireit.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;
import java.security.Key;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Key key;
    private final long validityInMs = 1000L * 60 * 60 * 10; // 10 hours

    public JwtUtil(@Value("${app.jwt.secret}") String secret) {
        if (secret == null || secret.isEmpty()) {
            secret = "change-me-change-me-change-me-change-me"; // dev fallback
        }
        this.key = Keys.hmacShaKeyFor(Arrays.copyOf(secret.getBytes(), 64));
    }

    public String generateToken(String username, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {
        Object roles = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("roles");
        if (roles instanceof List) {
            return ((List<?>) roles).stream().map(Object::toString).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}

package com.rideflowauthservice.service;


import com.rideflowauthservice.security.PassengerPrinciple;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtService{
    @Value("${jwt.expiration-ms}")
    private long expirationMs;
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey signingKey;

    @PostConstruct
    private void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(PassengerPrinciple passengerPrinciple) {
        return Jwts.builder()
                .subject(passengerPrinciple.getUserEmail())
                .claim("role", passengerPrinciple.getUser().getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey)
                .compact();
    }
    public String extractUserEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String expectedUseremail) {
        try {
            Claims claims = extractClaims(token);
            boolean useremailMatches = claims.getSubject().equals(expectedUseremail);
            boolean notExpired = claims.getExpiration().after(new Date());
            return useremailMatches && notExpired;
        } catch (JwtException e) {
            return false; // malformed, tampered, or expired — treat as invalid, don't throw
        }
    }
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

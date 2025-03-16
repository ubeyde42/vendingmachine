package com.ubeyde.sample.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret;
    private final long jwtExpirationInMs;
    private final String adminPassword;

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public JwtTokenProvider(
            @Value("${app.jwtSecret}") String jwtSecret,
            @Value("${app.jwtExpirationInMs}") long jwtExpirationInMs,
            @Value("${app.adminPassword}") String adminPassword) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.adminPassword = adminPassword;


    }

    public String generateToken(String password) {
        if (adminPassword.equals(password)) {
            return Jwts.builder()
                    .setSubject(password)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                    .signWith(SECRET_KEY)
                    .compact();
        }
        throw new IllegalArgumentException("Geçersiz şifre.");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAdminPasswordFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();  // Token'dan admin şifresini alıyoruz
    }
}

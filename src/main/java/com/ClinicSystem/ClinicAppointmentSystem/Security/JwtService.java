package com.ClinicSystem.ClinicAppointmentSystem.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
    private final String secret = "my-secret-key-for-clinic-system-is-long";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    public  String generateToken(String email , String role){
        return Jwts.builder().
        subject(email).
        claim("role", role).
        issuedAt(new Date()).
        expiration(new Date(System.currentTimeMillis()+ 1000 *60*60)).
        signWith(key).
        compact();
    }
    public String getEmail(String token){
        return Jwts.parser().
        verifyWith(key).
        build().
        parseSignedClaims(token).
        getPayload().
        getSubject();
    }
    public String getRole(String token){
        return Jwts.parser().
        verifyWith(key).
        build().
        parseSignedClaims(token).
        getPayload().
        get("role", String.class);
    }
}

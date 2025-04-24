package com.musafir.employyee.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String secret;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    private Jws<Claims> parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
    public String extractEmail(String token){
        return parseToken(token).getBody().getSubject();
    }
    private boolean isTokenExpired(String token){
        return parseToken(token).getBody().getExpiration().before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}

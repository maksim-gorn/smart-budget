package com.tpu.itr.smart_budget.authentication.JWT;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-hours}")
    private Long expiration;

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // генерация токена по id пользователя
    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // ложим id в тело токена
        return createToken(claims, String.valueOf(userId));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expiration*36*100000)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // id пользователя из токена
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    // извлечение даты истечения
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // не истёк ли токен
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // проверка токена
    public Boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Token is empty");
            return false;
        }

        try {
            Claims claims = extractAllClaims(token);
            boolean isExpired = claims.getExpiration().before(new Date());

            if (isExpired) {
                System.out.println("Token expired" + claims.getExpiration());
                return false;
            }

            System.out.println("Token is valid for user: " + claims.get("userId"));
            return true;

        } catch (MalformedJwtException e) {
            System.out.println("MalformedJwtException " + e.getMessage());
            return false;

        } catch (ExpiredJwtException e) {
            System.out.println("ExpiredJwtException " + e.getMessage());
            return false;

        }
    }

    // вспомогательные методы
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

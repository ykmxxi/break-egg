package com.example.loginpractice.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.example.loginpractice.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {

    public static String createToken(final User user, final String secretKey) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 600_000)) // 10분 (600_000 ms)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static String getUsername(final String token, final String secretKey) {
        return getClaims(token, secretKey)
                .getSubject();
    }

    public static boolean isExpired(final String token, final String secretKey) {
        Date expiration = getClaims(token, secretKey)
                .getExpiration();

        return expiration.before(new Date());
    }

    private static Claims getClaims(final String token, final String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}

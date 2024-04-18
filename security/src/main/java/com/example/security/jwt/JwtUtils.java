package com.example.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.data.util.Pair;

import com.example.security.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;

public class JwtUtils {

    /**
     * 토큰의 헤더에서 username 찾기
     *
     * @param token JWT 토큰
     * @return username
     */
    public static String getUsername(final String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * User 정보로 JWT Token 생성
     * HEADER: alg(암호화 알고리즘), kid
     * PAYLOAD: sub, iat, exp
     * SIGNATURE: JwtKey.getRandomKey()로 구현한 Secret Key로 HS512 해시
     *
     * @param user 유저
     * @return JWT Token
     */
    public static String createToken(final User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername()); // sub
        Date now = new Date(); // 현재 시간
        Pair<String, Key> key = JwtKey.getRandomKey();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 발행 시간
                .setExpiration(new Date(
                        now.getTime() + JwtProperties.EXPIRATION_TIME)) // 만료 시간(발행 시간 + 10분)
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid, pair의 첫 부분이 kid
                .signWith(key.getSecond()) // SIGNATURE, pair의 두 번째 부분이 SecretKey
                .compact();
    }

}

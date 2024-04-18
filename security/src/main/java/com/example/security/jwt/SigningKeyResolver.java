package com.example.security.jwt;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

/**
 * JwsHeader를 통해 SIGNATURE 검증에 필요한 Key를 가져오는 코드 구현
 */
public class SigningKeyResolver extends SigningKeyResolverAdapter {

    public static SigningKeyResolver instance = new SigningKeyResolver();

    @Override
    public Key resolveSigningKey(final JwsHeader header, final Claims claims) {
        String kid = header.getKeyId();

        if (kid == null) {
            return null;
        }
        return JwtKey.getKey(kid);
    }

}

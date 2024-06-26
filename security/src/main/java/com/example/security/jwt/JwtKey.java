package com.example.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.Random;

import org.springframework.data.util.Pair;

import io.jsonwebtoken.security.Keys;

/**
 * JWT Key를 제공하고 조회
 */
public class JwtKey {

    /**
     * Kid-Key List: 외부로 유출 X
     */
    private static final Map<String, String> SECRET_KEY_SET = Map.of(
            "key1",
            "SpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFunSpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFun",
            "key2",
            "GoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurity",
            "key3",
            "HelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurity"
    );
    private static final String[] KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]);
    private static final Random randomIndex = new Random();

    /**
     * SECRET_KEY_SET 에서 Random KEY 가져오기
     * @return kid : Key
     */
    public static Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)];
        String secretKey = SECRET_KEY_SET.get(kid);

        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * kid로 SecretKey 가져오기
     * @param kid kid
     * @return Key(SecretKey)
     */
    public static Key getKey(final String kid) {
        String key = SECRET_KEY_SET.getOrDefault(kid, null);

        if (key == null) {
            return null;
        }
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

}

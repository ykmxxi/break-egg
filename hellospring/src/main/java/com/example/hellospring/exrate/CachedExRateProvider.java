package com.example.hellospring.exrate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.hellospring.payment.ExRateProvider;

/**
 * Decorator: 데코레이터의 기능을 적용하고자 하는 오브젝트와 같은 인터페이스를 구현해야 한다
 * Client인 PaymentService가 CachedExRateProvider 오브젝트를 먼저 사용하고
 * WebApiExRateProvide를 사용하도록 만들어야 한다
 */
public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;
    private BigDecimal cachedExRate;
    private LocalDateTime cacheExpiryTime;

    public CachedExRateProvider(final ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExchangeRate(final String currency) throws IOException {
        if (cachedExRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) {
            cachedExRate = target.getExchangeRate(currency);
            cacheExpiryTime = LocalDateTime.now().plusSeconds(3); // 임의로 3초를 유효기간으로 지정
            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }

}

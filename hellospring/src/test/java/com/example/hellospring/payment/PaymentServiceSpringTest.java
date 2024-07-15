package com.example.hellospring.payment;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.hellospring.TestPaymentConfig;

// 아래 두 개의 어노테이션은 항상 같이 온다, 외우는게 좋음
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;
    @Autowired Clock clock;

    @Test
    @DisplayName("prepare() 금액 계산 테스트: Spring DI 사용")
    void convertedAmount() throws Exception {
        // exRate: 1000
        Payment payment1 = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment1.getExchangeRate()).isEqualByComparingTo(valueOf(1_000L));
        assertThat(payment1.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000L));

        // exRate: 500
        exRateProviderStub.setExRate(valueOf(500L));
        Payment payment2 = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment2.getExchangeRate()).isEqualByComparingTo(valueOf(500L));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000L));
    }

    @Test
    @DisplayName("prepare() 유효 기간 테스트")
    void validUntil() {
        Payment payment = paymentService.prepare(1L, "USD", TEN);
        LocalDateTime expectedValidUntil = LocalDateTime.now(this.clock)
                .plusMinutes(30L);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

}

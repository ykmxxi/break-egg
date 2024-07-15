package com.example.hellospring.payment;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.hellospring.exrate.WebApiExRateProvider;

class PaymentServiceTest {

    private Clock clock;

    @BeforeEach
    void setUp() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("잘못된 prepare() 테스트: 적용 환율, 원화 환산 금액 계산, 원화 환산 금액 유효시간 계산")
    void badPrepare() throws Exception {
        // 우리가 제어할 수 없는 외부 API에 의존해 기능을 검증하고 있음
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(), this.clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // API로 가져오는 환율 정보를 정확히 검증하기 어렵다, 제어할 수 없는 것에 영향을 받음
        assertThat(payment.getExchangeRate()).isNotNull();

        // 원화 환산 금액 계산은 비교적 정확히 검증하기 쉬움
        assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExchangeRate().multiply(payment.getForeignCurrencyAmount()));

        // 원화 환산 금액 유효시간 계산도 대략적인 검증이 가능, 정확한 검증은 아님
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30L));
    }

    @Test
    @DisplayName("prepare() 금액 계산 테스트: 테스트 대역(Test Double) 사용")
    void convertedAmount() throws Exception {
        testAmount(valueOf(500L), valueOf(5_000L), this.clock);
        testAmount(valueOf(1000L), valueOf(10_000L), this.clock);
        testAmount(valueOf(1300L), valueOf(13_000L), this.clock);
    }

    @Test
    @DisplayName("prepare() 유효 기간 테스트")
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1000L)), clock);

        // valid until이 prepare() 30분 뒤로 설정됐는가?
        Payment payment = paymentService.prepare(1L, "USD", TEN);
        LocalDateTime expectedValidUntil = LocalDateTime.now(this.clock)
                .plusMinutes(30L);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    private void testAmount(final BigDecimal exRate, final BigDecimal convertedAmount, final Clock clock) {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // BigDecimal은 숫자뿐만 아니라 유효 자릿수 모두를 따짐
        // isEqualTo()는 위험한 선택(같은 값을 가져도 유효 자릿수가 다르면 틀리다 할 수 있음)
        // isEqualByComparingTo() 사용
        assertThat(payment.getExchangeRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }

}

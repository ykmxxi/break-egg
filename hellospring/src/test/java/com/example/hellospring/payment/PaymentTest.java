package com.example.hellospring.payment;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", TEN, valueOf(1_000L), LocalDateTime.now(clock)
        );

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000L));
        assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30L));
    }

    @Test
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", TEN, valueOf(1_000L), LocalDateTime.now(clock)
        );

        assertThat(payment.isValid(clock)).isTrue();
        assertThat(payment.isValid(Clock.offset(clock, Duration.of(30L, ChronoUnit.MINUTES)))).isFalse();
    }

}

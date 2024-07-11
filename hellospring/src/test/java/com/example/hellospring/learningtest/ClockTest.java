package com.example.hellospring.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

class ClockTest {

    @Test
    void clock() {
        Clock clock = Clock.systemDefaultZone(); // 현재 시스템 기반의 시간대로 생성

        LocalDateTime now1 = LocalDateTime.now(clock);
        LocalDateTime now2 = LocalDateTime.now(clock);
        System.out.println("clock's now = " + now1);
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());

        assertThat(now2).isAfter(now1);
    }

    @Test
    void fixedClock() {
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime now1 = LocalDateTime.now(fixedClock);
        LocalDateTime now2 = LocalDateTime.now(fixedClock);

        assertThat(now2).isEqualTo(now1);
    }

}

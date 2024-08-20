package com.example.helloboot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.helloboot.HellobootTest;

@HellobootTest
class HelloRepositoryTest {

    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired HelloRepository helloRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    @Test
    @DisplayName("queryForObject(): 조회한 데이터가 없다면 EmptyResultDataAccessException 예외 발생")
    void findHelloFailed() {
        assertThat(helloRepository.findHello("spring")).isNull();
    }

    @Test
    void increaseCount() {
        String name = "spring";

        assertThat(helloRepository.countOf(name)).isEqualTo(0);

        helloRepository.increaseCount(name);
        assertThat(helloRepository.countOf(name)).isEqualTo(1);

        helloRepository.increaseCount(name);
        assertThat(helloRepository.countOf(name)).isEqualTo(2);
    }

}

package org.example.chap2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringCalculatorTest {

    @ParameterizedTest
    @CsvSource(value = {"=0", "1,2=3", "1,2,3=6", "1,2:3=6"}, delimiter = '=')
    void 기본_문자열(String input, int expected) {
        Splitter splitter = new Splitter(input);
        StringCalculator calculator = new StringCalculator(splitter);

        assertThat(calculator.splitAndSum()).isEqualTo(expected);
    }

    @Test
    void 커스텀_문자열() {
        Splitter splitter = new Splitter("//-\n1-2-3");
        StringCalculator calculator = new StringCalculator(splitter);

        assertThat(calculator.splitAndSum()).isEqualTo(6);
    }

}

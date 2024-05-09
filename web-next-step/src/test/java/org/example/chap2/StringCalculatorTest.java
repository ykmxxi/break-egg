package org.example.chap2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    @Test
    void add_null_or_empty() {
        assertThat(calculator.add(null)).isEqualTo(0);
        assertThat(calculator.add("")).isEqualTo(0);
    }

    @Test
    void 숫자_하나() {
        assertThat(calculator.add("1")).isEqualTo(1);
    }

    @Test
    void 쉼표_구분자() {
        assertThat(calculator.add("1,2,3")).isEqualTo(6);
    }

    @Test
    void 쉼표_콜론_구분자() {
        assertThat(calculator.add("1,2:3")).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자() {
        assertThat(calculator.add("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    void 음수_포함_RuntimeException() {
        assertThatThrownBy(() -> calculator.add("-1,2:3"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("음수는 입력할 수 없습니다.");
    }

}

package org.example.chap2;

import java.util.List;

public class StringCalculator {

    private final Splitter splitter;

    public StringCalculator(final Splitter splitter) {
        this.splitter = splitter;
    }

    public int splitAndSum() {
        List<Integer> numbers = splitter.split();

        if (numbers.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (int number : numbers) {
            validate(number);
            sum += number;
        }
        return sum;
    }

    private void validate(final int number) {
        if (number < 0) {
            throw new RuntimeException("음수를 입력할 수 없습니다.");
        }
    }

}

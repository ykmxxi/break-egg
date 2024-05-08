package org.example.chap2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Splitter {

    private static final Pattern pattern = Pattern.compile("^[/{2}.\n]");

    private final String input;

    public Splitter(String input) {
        this.input = input;
    }

    public List<Integer> split() {
        List<Integer> numbers = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return numbers;
        }
        if (isCustomDelimiter()) {
            String[] split = input.split("\\n");
            return Arrays.stream(split[1].split(String.valueOf(split[0].charAt(2))))
                    .map(Integer::parseInt)
                    .toList();
        }
        return Arrays.stream(input.split("[,:]"))
                .map(Integer::parseInt)
                .toList();
    }

    private boolean isCustomDelimiter() {
        return pattern.matcher(input).find();
    }

}

package org.example.chap2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SplitterTest {

    private Splitter splitter;

    @Test
    void null_Empty() {
        splitter = new Splitter("");

        assertThat(splitter.split()).isEmpty();
    }

    @Test
    void custom_Delimiter() {
        splitter = new Splitter("//-\n1-2-3");

        assertThat(splitter.split()).containsExactly(1, 2, 3);
    }

    @Test
    void test() {
        splitter = new Splitter("1,2:3");

        assertThat(splitter.split()).containsExactly(1, 2, 3);
    }

}

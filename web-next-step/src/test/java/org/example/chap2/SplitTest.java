package org.example.chap2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SplitTest {

    @Test
    void split() {
        assertThat("1".split(",")).containsExactly("1");

        assertThat("1,2".split(",")).containsExactly("1", "2");
    }

}

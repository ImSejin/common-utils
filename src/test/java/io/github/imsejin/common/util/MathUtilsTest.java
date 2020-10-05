package io.github.imsejin.common.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void fibonacci() {
        // given
        int[] input = {0, 1, 2, 5, 7, 10, 13, 17, 25, 50, 100};
        String[] expected = {"0", "1", "1", "5", "13", "55", "233", "1597", "75025", "12586269025", "354224848179261915075"};

        for (int i = 0; i < input.length; i++) {
            // when
            BigInteger result = MathUtils.fibonacci(input[i]);

            // then
            assertEquals(result.toString(), expected[i]);
        }
    }

    @Test
    void factorial() {
        // given
        int[] input = {5, 7, 10, 13, 17, 25};
        String[] expected = {"120", "5040", "3628800", "6227020800", "355687428096000", "15511210043330985984000000"};

        for (int i = 0; i < input.length; i++) {
            // when
            BigInteger result = MathUtils.factorial(input[i]);

            // then
            assertEquals(result.toString(), expected[i]);
        }
    }

    @Test
    void gcd() {
        // given
        int number = 5;

        // when

        // then

    }

    @Test
    void isPrime() {
        // given
        int number = 5;

        // when

        // then

    }

}

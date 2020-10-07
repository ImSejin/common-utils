package io.github.imsejin.common.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

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
            assertThat(result.toString()).isEqualTo(expected[i]);
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
            assertThat(result.toString()).isEqualTo(expected[i]);
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

    @ParameterizedTest
    @ValueSource(doubles = {1.248458248, 0.1575153545, 854.0912738218, 97234.10398570893174})
    void ceil(double amount) {
        // when
        int len = 1;
        double result = MathUtils.ceil(amount, len);

        // then
        String expected = String.valueOf(amount);
        expected = expected.substring(0, expected.indexOf('.') + len + 1);
        String[] strings = expected.split("\\.");
        expected = strings[0] + '.' + (Integer.parseInt(strings[1]) + 1);
        assertThat(expected).isEqualTo(String.valueOf(result));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.248458248, 0.1575153545, 854.0912738218, 97234.10398570893174})
    void floor(double amount) {
        // when
        int len = 6;
        double result = MathUtils.floor(amount, len);

        // then
        String expected = String.valueOf(amount);
        expected = expected.substring(0, expected.indexOf('.') + len + 1);
        assertThat(expected).isEqualTo(String.valueOf(result));
    }

}

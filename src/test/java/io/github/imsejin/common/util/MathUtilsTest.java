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
            assertThat(result.toString())
                    .as("Gets fibonacci's number")
                    .isEqualTo(expected[i]);
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
            assertThat(result.toString())
                    .as("Gets factorial")
                    .isEqualTo(expected[i]);
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
    @ValueSource(doubles = {0.1575153545, 1.248458248, 854.0812738218, 97234.10398570893174})
    void ceil(double amount) {
        // when
        int len = 1;
        double actual = MathUtils.ceil(amount, len);

        // then
        String expected = String.valueOf(amount);
        expected = expected.substring(0, expected.indexOf('.') + len + 1);
        String[] strings = expected.split("\\.");
        expected = strings[0] + '.' + (Integer.parseInt(strings[1]) + 1);
        assertThat(String.valueOf(actual))
                .as("Ceil decimal")
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1575153545, 1.248458248, 854.0812738218, 97234.10398570893174})
    void round(double amount) {
        // when
        int len = 2;
        double actual = MathUtils.round(amount, len);

        // then
        String[] split = String.valueOf(amount).split("\\.");
        String integerPart = split[0];
        String decimalPart = StringUtils.match("^\\d{" + (len + 1) + "}", split[1]);
        int lastDigit = Integer.parseInt(StringUtils.getLastString(decimalPart));
        decimalPart = StringUtils.chop(decimalPart);
        if (lastDigit > 4) {
            decimalPart = StringUtils.chop(decimalPart) + (Integer.parseInt(StringUtils.getLastString(decimalPart)) + 1);
        }
        String expected = (integerPart + '.' + decimalPart).replaceAll("0+$", "");
        assertThat(String.valueOf(actual))
                .as("Selectively ceil or floor decimal")
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1575153545, 1.248458248, 854.0812738218, 97234.10398570893174})
    void floor(double amount) {
        // when
        int len = 6;
        double actual = MathUtils.floor(amount, len);

        // then
        String expected = String.valueOf(amount);
        expected = expected.substring(0, expected.indexOf('.') + len + 1);
        assertThat(String.valueOf(actual))
                .as("Floor decimal")
                .isEqualTo(expected);
    }

}

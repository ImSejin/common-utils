/*
 * Copyright 2021 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("DoubleAssert")
class DoubleAssertTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.DoubleAssertTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.DoubleAssertTest#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(double actual, double expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(double actual, double expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("passes, when actual is not equal to other")
        void test0(double actual, double expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(double actual, double expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @Test
        @DisplayName("passes, when actual is greater than other")
        void test0() {
            Map<Double, Double> map = new HashMap<>();
            map.put(1.1D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(32), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 0D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(31));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0() {
            Map<Double, Double> map = new HashMap<>();
            map.put(1.14D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(31), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            Map<Double, Double> map = new HashMap<>();
            map.put(-1.16D, (double) Character.valueOf('\u0000'));
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(32));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when actual is less than other")
        void test0() {
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 1.141D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(32));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            Map<Double, Double> map = new HashMap<>();
            map.put(1.99D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(31), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is less than or equal to other")
        void test0() {
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 1.0D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(31));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), -1.001D);
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(32), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(doubles = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(double actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(doubles = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE, -Double.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(double actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(doubles = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(double actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE, -Double.MAX_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(double actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(doubles = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE, -Double.MAX_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(double actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(doubles = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(double actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(doubles = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, -Float.MAX_VALUE, -Double.MAX_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(double actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(doubles = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(double actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasDecimalPart'")
    class HasDecimalPart {
        @ParameterizedTest
        @ValueSource(doubles = {
                -123.456, -0.123456, -Float.MIN_VALUE, -Double.MIN_VALUE,
                Double.MIN_VALUE, Float.MIN_VALUE, 0.123456, 123.456,
        })
        @DisplayName("passes, when actual has decimal part")
        void test0(double actual) {
            assertThatCode(() -> Asserts.that(actual).hasDecimalPart())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -123456.0, -Float.MAX_VALUE, -Double.MAX_VALUE, 0,
                Double.MAX_VALUE, Float.MAX_VALUE, 1.0, 123456.0,
        })
        @DisplayName("throws exception, when actual doesn't have decimal part")
        void test1(double actual) {
            assertThatCode(() -> Asserts.that(actual).hasDecimalPart())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Double, Double> map = new HashMap<>();
        map.put((double) Character.valueOf('\u0000'), 0D);
        map.put(-1.024D, 1.024D * -1);
        map.put(31D, Double.valueOf(31));
        map.put(Double.MIN_VALUE, Double.MIN_VALUE);
        map.put(Double.MAX_VALUE, Double.MAX_VALUE);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Double, Double> map = new HashMap<>();
        map.put((double) Character.valueOf('a'), 0D);
        map.put(1.024D, -1.024D);
        map.put(31D, (double) (31 >> 7));
        map.put(Double.MIN_VALUE, -1.7976931348623157E308D);
        map.put(Double.MAX_VALUE, 4.9E-324D);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}

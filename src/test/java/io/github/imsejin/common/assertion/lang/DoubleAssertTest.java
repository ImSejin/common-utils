/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

@DisplayName("DoubleAssert")
class DoubleAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.lang.DoubleAssertTest";
    private static final String EQUALITY = FQCN + "#equality";
    private static final String NON_EQUALITY = FQCN + "#nonEquality";

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
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put(1.1D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(32), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 0D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(31));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            // except
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
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put(1.14D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(31), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put(-1.16D, (double) Character.valueOf('\u0000'));
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(32));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            // except
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
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 1.141D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(32));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put(1.99D, (double) Character.valueOf('\u0000'));
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(31), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            // except
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
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), 1.0D);
            map.put(-1.024D, 1.024D);
            map.put(31D, Double.valueOf(31));
            map.put(0D, Double.MAX_VALUE);
            map.put(-Double.MAX_VALUE, 0D);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Double, Double> map = new HashMap<>();
            map.put((double) Character.valueOf('\u0000'), -1.001D);
            map.put(1.024D, -1.024D);
            map.put(Double.valueOf(32), 31D);
            map.put(Double.MAX_VALUE, 0D);
            map.put(0D, -Double.MAX_VALUE);

            // except
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
    @DisplayName("method 'isBetween'")
    class IsBetween {
        @Test
        @DisplayName("passes, when actual is between x and y inclusively")
        void test0() {
            new Random().doubles(-100, 50).limit(10_000)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, n + 0.1);
                        Asserts.that(n).isBetween(n - 0.1, n);
                        Asserts.that(n).isBetween(n - 0.1, n + 0.1);
                    }));
        }


        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<Double> doubles = new Random().doubles(-100, 50)
                    .limit(10_000).boxed().collect(toList());

            // except
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, n - 0.1)));
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 0.1, n)));
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 0.1, n - 0.1)));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            new Random().doubles(-100, 50)
                    .limit(10_000).forEach(n -> assertThatNoException()
                            .isThrownBy(() -> Asserts.that(n).as("{0} < {1} < {2}", n - 1, n, n + 1).isStrictlyBetween(n - 1, n + 1)));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<Double> doubles = new Random().doubles(-100, 50)
                    .limit(10_000).boxed().collect(toList());

            // except
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n + 0.1)));
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n - 0.1, n)));
            doubles.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n + 0.1, n - 0.1)));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isCloseTo'")
    class IsCloseTo {
        @Test
        @DisplayName("passes, when actual is close to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Double.MAX_VALUE).isCloseTo(Double.MAX_VALUE, 0);
                Asserts.that(Double.MAX_VALUE).isCloseTo(Double.MAX_VALUE * 0.9, 10);
                Asserts.that(123_456_789.012).isCloseTo(98_765.432, 99.93);
                Asserts.that(1024.0).isCloseTo(32.0, 96.875);
                Asserts.that(100.05).isCloseTo(93.99, 7.01);
                Asserts.that(64.0).isCloseTo(16.0, 75);
                Asserts.that(5.0).isCloseTo(4.5, 12.5);
                Asserts.that(Math.PI).isCloseTo(Math.PI, Double.MIN_VALUE);
                Asserts.that(Math.sqrt(2)).isCloseTo(1.414213, 4.0E-5);
                Asserts.that(-Math.PI).isCloseTo(-3.141592, 2.1E-5);
                Asserts.that(-5.0).isCloseTo(-4.0, 20);
                Asserts.that(-5.0).isCloseTo(-4.5, 10);
                Asserts.that(-33.701).isCloseTo(-3.64, 90.91);
                Asserts.that(-100.0).isCloseTo(-125.0, 25);
                Asserts.that(-500.0).isCloseTo(-499.3, 0.2);
                Asserts.that(-87_654_321.098).isCloseTo(-12_345.678, 99.986);
                Asserts.that(4.9E-200).isCloseTo(4.8E-200, 2.05);
                Asserts.that(Double.MIN_VALUE).isCloseTo(Double.MIN_VALUE, 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(36.5).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.MAX_VALUE).isCloseTo(Double.MAX_VALUE * 0.9, 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.MAX_VALUE).isCloseTo(Double.MIN_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(64.01).isCloseTo(32.01, 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(1.0).isCloseTo(0.0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-1.0).isCloseTo(0.0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-20.6).isCloseTo(-15.0, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(4.9E-200).isCloseTo(4.8E-200, 2))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.NaN).isCloseTo(0.0, 99.9))
                    .withMessage("It is expected to close to other, but it isn't. (expected: '0.0', actual: 'NaN')");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.NEGATIVE_INFINITY).isCloseTo(0.0, 99.9))
                    .withMessage("It is expected to close to other, but it isn't. (expected: '0.0', actual: '-Infinity')");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.POSITIVE_INFINITY).isCloseTo(0.1, 99.9))
                    .withMessage("It is expected to close to other, but it isn't. (expected: '0.1', actual: 'Infinity')");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Double.MIN_VALUE).isCloseTo(Double.MAX_VALUE, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0.0).isCloseTo(1.0, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0.0).isCloseTo(-1.0, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
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
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).hasDecimalPart());
        }

        @ParameterizedTest
        @ValueSource(doubles = {
                -123456.0, -Float.MAX_VALUE, -Double.MAX_VALUE, 0,
                Double.MAX_VALUE, Float.MAX_VALUE, 1.0, 123456.0,
        })
        @DisplayName("throws exception, when actual doesn't have decimal part")
        void test1(double actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).hasDecimalPart())
                    .withMessageStartingWith("It is expected to have decimal part, but it isn't.");
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

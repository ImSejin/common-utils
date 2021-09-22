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

package io.github.imsejin.common.assertion.math;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("BigIntegerAssert")
class BigIntegerAssertTest {

    private static final String IS_EQUAL_TO = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isEqualTo";
    private static final String IS_NOT_EQUAL_TO = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isNotEqualTo";
    private static final String IS_GREATER_THAN = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isGreaterThan";
    private static final String IS_GREATER_THAN_OR_EQUAL_TO = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isGreaterThanOrEqualTo";
    private static final String IS_LESS_THAN = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isLessThan";
    private static final String IS_LESS_THAN_OR_EQUAL_TO = "io.github.imsejin.common.assertion.math.BigIntegerAssertTest#isLessThanOrEqualTo";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("passes, when actual is equal to other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_NOT_EQUAL_TO)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected))
                    .withMessageStartingWith("They are expected to be equal, but they aren't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @MethodSource(IS_NOT_EQUAL_TO)
        @DisplayName("passes, when actual is not equal to other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .withMessageStartingWith("They are expected to be not equal, but they are.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN)
        @DisplayName("passes, when actual is greater than other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_LESS_THAN_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected))
                    .withMessageStartingWith("It is expected to be greater than the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN_OR_EQUAL_TO)
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_LESS_THAN)
        @DisplayName("throws exception, when actual is less than other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be greater than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @ParameterizedTest
        @MethodSource(IS_LESS_THAN)
        @DisplayName("passes, when actual is less than other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLessThan(expected))
                    .withMessageStartingWith("It is expected to be less than the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @ParameterizedTest
        @MethodSource(IS_LESS_THAN_OR_EQUAL_TO)
        @DisplayName("passes, when actual is less than or equal to other")
        void test0(BigInteger actual, BigInteger expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN)
        @DisplayName("throws exception, when actual is greater than other")
        void test1(BigInteger actual, BigInteger expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be less than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "1", "128", "1024", "65535", "2147483647",
                "9223372036854775807", "8770435964971880478329856",
        })
        @DisplayName("passes, when actual is positive")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new BigInteger(actual)).isPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "-8770435964971880478329856", "-9223372036854775807",
                "-2147483647", "-65535", "-1024", "-128", "-1", "0",
        })
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(String actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new BigInteger(actual)).isPositive())
                    .withMessageStartingWith("It is expected to be positive, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "0", "1", "128", "1024", "65535", "2147483647",
                "9223372036854775807", "8770435964971880478329856",
        })
        @DisplayName("passes, when actual is zero or positive")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new BigInteger(actual)).isZeroOrPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "-8770435964971880478329856", "-9223372036854775807",
                "-2147483647", "-65535", "-1024", "-128", "-1",
        })
        @DisplayName("throws exception, when actual is negative")
        void test1(String actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new BigInteger(actual)).isZeroOrPositive())
                    .withMessageStartingWith("It is expected to be zero or positive, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "-8770435964971880478329856", "-9223372036854775807",
                "-2147483647", "-65535", "-1024", "-128", "-1",
        })
        @DisplayName("passes, when actual is negative")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new BigInteger(actual)).isNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "0", "1", "128", "1024", "65535", "2147483647",
                "9223372036854775807", "8770435964971880478329856",
        })
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(String actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new BigInteger(actual)).isNegative())
                    .withMessageStartingWith("It is expected to be negative, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "-8770435964971880478329856", "-9223372036854775807",
                "-2147483647", "-65535", "-1024", "-128", "-1", "0",
        })
        @DisplayName("passes, when actual is zero or negative")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new BigInteger(actual)).isZeroOrNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1", "128", "1024", "65535", "2147483647",
                "9223372036854775807", "8770435964971880478329856",
        })
        @DisplayName("throws exception, when actual is positive")
        void test1(String actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new BigInteger(actual)).isZeroOrNegative())
                    .withMessageStartingWith("It is expected to be zero or negative, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isBetween'")
    class IsBetween {
        @Test
        @DisplayName("passes, when actual is between x and y inclusively")
        void test0() {
            IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1).limit(10_000).mapToObj(BigInteger::valueOf)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        BigInteger startInclusive = n.subtract(BigInteger.ONE);
                        BigInteger endInclusive = n.add(BigInteger.ONE);

                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, endInclusive);
                        Asserts.that(n).isBetween(startInclusive, n);
                        Asserts.that(n).isBetween(startInclusive, endInclusive);
                    }));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<BigInteger> bigInts = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(BigInteger::valueOf).collect(toList());

            // except
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, n.subtract(BigInteger.ONE))));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n.add(BigInteger.ONE), n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n.add(BigInteger.ONE), n.subtract(BigInteger.ONE))));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(BigInteger::valueOf).forEach(n -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(n)
                            .isStrictlyBetween(n.subtract(BigInteger.ONE), n.add(BigInteger.ONE))));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<BigInteger> bigInts = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(BigInteger::valueOf).collect(toList());

            // except
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n.add(BigInteger.ONE))));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n.subtract(BigInteger.ONE), n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n.add(BigInteger.ONE), n.subtract(BigInteger.ONE))));
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
                Asserts.that(BigInteger.valueOf(Integer.MAX_VALUE)).isCloseTo(BigInteger.valueOf(Integer.MAX_VALUE), 0);
                Asserts.that(BigInteger.valueOf(Integer.MAX_VALUE)).isCloseTo(BigInteger.valueOf((long) (Integer.MAX_VALUE * 0.25)), 75.1);
                Asserts.that(BigInteger.valueOf(123_456_789)).isCloseTo(BigInteger.valueOf(98_765), 99.93);
                Asserts.that(BigInteger.valueOf(1024)).isCloseTo(BigInteger.valueOf(32), 96.875);
                Asserts.that(BigInteger.valueOf(100)).isCloseTo(BigInteger.valueOf(93), 7.01);
                Asserts.that(BigInteger.valueOf(64)).isCloseTo(BigInteger.valueOf(16), 75);
                Asserts.that(BigInteger.valueOf(-5)).isCloseTo(BigInteger.valueOf(-4), 20);
                Asserts.that(BigInteger.valueOf(-33)).isCloseTo(BigInteger.valueOf(-3), 90.91);
                Asserts.that(BigInteger.valueOf(-500)).isCloseTo(BigInteger.valueOf(-499), 0.2);
                Asserts.that(BigInteger.valueOf(-87_654_321)).isCloseTo(BigInteger.valueOf(-12_345), 99.986);
                Asserts.that(BigInteger.valueOf(Integer.MIN_VALUE)).isCloseTo(BigInteger.valueOf((long) (Integer.MIN_VALUE * 0.25)), 75);
                Asserts.that(BigInteger.valueOf(Integer.MIN_VALUE)).isCloseTo(BigInteger.valueOf(Integer.MIN_VALUE), 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.TEN).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(Integer.MAX_VALUE)).isCloseTo(BigInteger.valueOf((long) (Integer.MAX_VALUE * 0.25)), 75))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(Integer.MAX_VALUE)).isCloseTo(BigInteger.valueOf(Integer.MIN_VALUE), 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(64)).isCloseTo(BigInteger.valueOf(32), 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.ONE).isCloseTo(BigInteger.ZERO, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.ONE.negate()).isCloseTo(BigInteger.ZERO, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(-20)).isCloseTo(BigInteger.valueOf(-15), 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(Integer.MIN_VALUE)).isCloseTo(BigInteger.valueOf(Integer.MAX_VALUE), 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.valueOf(Integer.MIN_VALUE)).isCloseTo(BigInteger.valueOf((long) (Integer.MIN_VALUE * 0.9)), 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.ZERO).isCloseTo(BigInteger.ONE, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigInteger.ZERO).isCloseTo(BigInteger.ONE.negate(), 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> isEqualTo() {
        return Stream.of(
                Arguments.of(BigInteger.ZERO, new BigInteger("0")),
                Arguments.of(BigInteger.ONE, BigInteger.valueOf(1)),
                Arguments.of(BigInteger.TEN, BigDecimal.valueOf(10.6).toBigInteger()),
                Arguments.of(new BigInteger("ffffff", 16), BigInteger.valueOf(16777215)),
                Arguments.of(new BigInteger("18446744073709551614"), BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(2)))
        );
    }

    private static Stream<Arguments> isNotEqualTo() {
        return Stream.of(
                Arguments.of(BigInteger.ZERO, new BigInteger("10")),
                Arguments.of(BigInteger.ONE, BigInteger.ZERO),
                Arguments.of(BigInteger.TEN, BigDecimal.valueOf(11).toBigInteger()),
                Arguments.of(new BigInteger("ffffff", 16), BigInteger.valueOf(-16777215)),
                Arguments.of(new BigInteger(Long.MAX_VALUE + "" + Long.MAX_VALUE), BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(2)))
        );
    }

    private static Stream<Arguments> isGreaterThan() {
        return Stream.of(
                Arguments.of(new BigInteger("-512"), BigInteger.valueOf(-1024)),
                Arguments.of(BigInteger.ZERO, BigInteger.ONE.negate()),
                Arguments.of(BigInteger.ONE, BigInteger.ZERO),
                Arguments.of(BigInteger.TEN, new BigInteger("0001")),
                Arguments.of(BigInteger.valueOf(Byte.MAX_VALUE), BigInteger.TEN)
        );
    }

    private static Stream<Arguments> isGreaterThanOrEqualTo() {
        return Stream.concat(isGreaterThan(), isEqualTo());
    }

    private static Stream<Arguments> isLessThan() {
        return Stream.of(
                Arguments.of(BigInteger.valueOf(-1024), new BigInteger("-512")),
                Arguments.of(BigInteger.ONE.negate(), BigInteger.ZERO),
                Arguments.of(BigInteger.ZERO, BigInteger.ONE),
                Arguments.of(new BigInteger("0001"), BigInteger.TEN),
                Arguments.of(BigInteger.TEN, BigInteger.valueOf(Byte.MAX_VALUE))
        );
    }

    private static Stream<Arguments> isLessThanOrEqualTo() {
        return Stream.concat(isLessThan(), isEqualTo());
    }

    private static Stream<Arguments> isPositive() {
        return Stream.of(
                Arguments.of()
        );
    }

    private static Stream<Arguments> isZero() {
        return Stream.of(Arguments.of(BigInteger.ZERO));
    }

    private static Stream<Arguments> isZeroOrPositive() {
        return Stream.concat(isZero(), isPositive());
    }

    private static Stream<Arguments> isNegative() {
        return Stream.of(
                Arguments.of()
        );
    }

    private static Stream<Arguments> isZeroOrNegative() {
        return Stream.concat(isZero(), isNegative());
    }

}

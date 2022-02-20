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

@DisplayName("BigDecimalAssert")
class BigDecimalAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.math.BigDecimalAssertTest";
    private static final String IS_EQUAL_TO = FQCN + "#isEqualTo";
    private static final String IS_NOT_EQUAL_TO = FQCN + "#isNotEqualTo";
    private static final String IS_GREATER_THAN = FQCN + "#isGreaterThan";
    private static final String IS_GREATER_THAN_OR_EQUAL_TO = FQCN + "#isGreaterThanOrEqualTo";
    private static final String IS_LESS_THAN = FQCN + "#isLessThan";
    private static final String IS_LESS_THAN_OR_EQUAL_TO = FQCN + "#isLessThanOrEqualTo";
    private static final String IS_POSITIVE = FQCN + "#isPositive";
    private static final String IS_ZERO_OR_POSITIVE = FQCN + "#isZeroOrPositive";
    private static final String IS_NEGATIVE = FQCN + "#isNegative";
    private static final String IS_ZERO_OR_NEGATIVE = FQCN + "#isZeroOrNegative";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("passes, when actual is equal to other")
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_NOT_EQUAL_TO)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_LESS_THAN_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_LESS_THAN)
        @DisplayName("throws exception, when actual is less than other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        void test0(BigDecimal actual, BigDecimal expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_GREATER_THAN)
        @DisplayName("throws exception, when actual is greater than other")
        void test1(BigDecimal actual, BigDecimal expected) {
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
        @MethodSource(IS_POSITIVE)
        @DisplayName("passes, when actual is positive")
        void test0(BigDecimal actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isPositive());
        }

        @ParameterizedTest
        @MethodSource(IS_ZERO_OR_NEGATIVE)
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(BigDecimal actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isPositive())
                    .withMessageStartingWith("It is expected to be positive, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @MethodSource(IS_ZERO_OR_POSITIVE)
        @DisplayName("passes, when actual is zero or positive")
        void test0(BigDecimal actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrPositive());
        }

        @ParameterizedTest
        @MethodSource(IS_NEGATIVE)
        @DisplayName("throws exception, when actual is negative")
        void test1(BigDecimal actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isZeroOrPositive())
                    .withMessageStartingWith("It is expected to be zero or positive, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @MethodSource(IS_NEGATIVE)
        @DisplayName("passes, when actual is negative")
        void test0(BigDecimal actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNegative());
        }

        @ParameterizedTest
        @MethodSource(IS_ZERO_OR_POSITIVE)
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(BigDecimal actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNegative())
                    .withMessageStartingWith("It is expected to be negative, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @MethodSource(IS_ZERO_OR_NEGATIVE)
        @DisplayName("passes, when actual is zero or negative")
        void test0(BigDecimal actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrNegative());
        }

        @ParameterizedTest
        @MethodSource(IS_POSITIVE)
        @DisplayName("throws exception, when actual is positive")
        void test1(BigDecimal actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isZeroOrNegative())
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
            IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1).limit(10_000).mapToObj(BigDecimal::valueOf)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        BigDecimal startInclusive = n.subtract(BigDecimal.ONE);
                        BigDecimal endInclusive = n.add(BigDecimal.ONE);

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
            List<BigDecimal> bigInts = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(BigDecimal::valueOf).collect(toList());

            // except
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, n.subtract(BigDecimal.ONE))));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n.add(BigDecimal.ONE), n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n.add(BigDecimal.ONE), n.subtract(BigDecimal.ONE))));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            for (int i = Integer.MIN_VALUE; i < Integer.MIN_VALUE + 10_000; i++) {
                BigDecimal bigInt = BigDecimal.valueOf(i);
                assertThatNoException().isThrownBy(() -> Asserts.that(bigInt)
                        .isStrictlyBetween(bigInt.subtract(BigDecimal.ONE), bigInt.add(BigDecimal.ONE)));
            }
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<BigDecimal> bigInts = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(BigDecimal::valueOf).collect(toList());

            // except
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n.add(BigDecimal.ONE))));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n.subtract(BigDecimal.ONE), n)));
            bigInts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n.add(BigDecimal.ONE), n.subtract(BigDecimal.ONE))));
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
                Asserts.that(BigDecimal.valueOf(Integer.MAX_VALUE)).isCloseTo(BigDecimal.valueOf(Integer.MAX_VALUE), 0);
                Asserts.that(BigDecimal.valueOf(Integer.MAX_VALUE)).isCloseTo(BigDecimal.valueOf((long) (Integer.MAX_VALUE * 0.25)), 75.1);
                Asserts.that(BigDecimal.valueOf(123_456_789)).isCloseTo(BigDecimal.valueOf(98_765), 99.93);
                Asserts.that(BigDecimal.valueOf(1024)).isCloseTo(BigDecimal.valueOf(32), 96.875);
                Asserts.that(BigDecimal.valueOf(100)).isCloseTo(BigDecimal.valueOf(93), 7.01);
                Asserts.that(BigDecimal.valueOf(64)).isCloseTo(BigDecimal.valueOf(16), 75);
                Asserts.that(BigDecimal.valueOf(-5)).isCloseTo(BigDecimal.valueOf(-4), 20);
                Asserts.that(BigDecimal.valueOf(-33)).isCloseTo(BigDecimal.valueOf(-3), 90.91);
                Asserts.that(BigDecimal.valueOf(-500)).isCloseTo(BigDecimal.valueOf(-499), 0.2);
                Asserts.that(BigDecimal.valueOf(-87_654_321)).isCloseTo(BigDecimal.valueOf(-12_345), 99.986);
                Asserts.that(BigDecimal.valueOf(Integer.MIN_VALUE)).isCloseTo(BigDecimal.valueOf((long) (Integer.MIN_VALUE * 0.25)), 75);
                Asserts.that(BigDecimal.valueOf(Integer.MIN_VALUE)).isCloseTo(BigDecimal.valueOf(Integer.MIN_VALUE), 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.TEN).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(Integer.MAX_VALUE)).isCloseTo(BigDecimal.valueOf((long) (Integer.MAX_VALUE * 0.25)), 75))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(Integer.MAX_VALUE)).isCloseTo(BigDecimal.valueOf(Integer.MIN_VALUE), 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(64)).isCloseTo(BigDecimal.valueOf(32), 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.ONE).isCloseTo(BigDecimal.ZERO, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.ONE.negate()).isCloseTo(BigDecimal.ZERO, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(-20)).isCloseTo(BigDecimal.valueOf(-15), 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(Integer.MIN_VALUE)).isCloseTo(BigDecimal.valueOf(Integer.MAX_VALUE), 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.valueOf(Integer.MIN_VALUE)).isCloseTo(BigDecimal.valueOf((long) (Integer.MIN_VALUE * 0.9)), 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.ZERO).isCloseTo(BigDecimal.ONE, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(BigDecimal.ZERO).isCloseTo(BigDecimal.ONE.negate(), 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'hasDecimalPart'")
    class HasDecimalPart {
        @ParameterizedTest
        @ValueSource(strings = {
                "-87704359649718804783.29856",
                "-922337203685477.5807",
                "-2147483.647",
                "-655.35",
                "-102.4",
                "-1.01",
                "1.01",
                "102.4",
                "655.35",
                "2147483.647",
                "922337203685477.5807",
                "87704359649718804783.29856",
        })
        @DisplayName("passes, when actual has decimal part")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new BigDecimal(actual)).hasDecimalPart());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "-8770435964971880478329856",
                "-9223372036854775807",
                "-2147483647",
                "-65535",
                "-1024",
                "-101",
                "-1",
                "0",
                "1",
                "101",
                "1024",
                "65535",
                "2147483647",
                "9223372036854775807",
                "8770435964971880478329856",
        })
        @DisplayName("throws exception, when actual doesn't have decimal part")
        void test1(String actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new BigDecimal(actual)).hasDecimalPart())
                    .withMessageStartingWith("It is expected to have decimal part, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> isEqualTo() {
        return Stream.of(
                Arguments.of(BigDecimal.ZERO, new BigDecimal("0")),
                Arguments.of(BigDecimal.ONE, BigDecimal.valueOf(1.00)),
                Arguments.of(BigDecimal.TEN, BigDecimal.valueOf(10.0)),
                Arguments.of(new BigDecimal(new BigInteger("ffffff", 16)), new BigDecimal(BigInteger.valueOf(16777215))),
                Arguments.of(new BigDecimal("18446744073709551614"), BigDecimal.valueOf(Long.MAX_VALUE).multiply(BigDecimal.valueOf(2)))
        );
    }

    private static Stream<Arguments> isNotEqualTo() {
        return Stream.of(
                Arguments.of(BigDecimal.ZERO, new BigDecimal("10")),
                Arguments.of(BigDecimal.ONE, BigDecimal.ZERO),
                Arguments.of(BigDecimal.TEN, BigDecimal.valueOf(11)),
                Arguments.of(new BigDecimal(new BigInteger("ffffff", 16)), new BigDecimal(BigInteger.valueOf(-16777215))),
                Arguments.of(new BigDecimal(Long.MAX_VALUE + "" + Long.MAX_VALUE), BigDecimal.valueOf(Long.MAX_VALUE).multiply(BigDecimal.valueOf(2)))
        );
    }

    private static Stream<Arguments> isGreaterThan() {
        return Stream.of(
                Arguments.of(new BigDecimal("-512"), BigDecimal.valueOf(-1024)),
                Arguments.of(BigDecimal.ZERO, BigDecimal.ONE.negate()),
                Arguments.of(BigDecimal.ONE, BigDecimal.ZERO),
                Arguments.of(BigDecimal.TEN, new BigDecimal("0001")),
                Arguments.of(BigDecimal.valueOf(Byte.MAX_VALUE), BigDecimal.TEN)
        );
    }

    private static Stream<Arguments> isGreaterThanOrEqualTo() {
        return Stream.concat(isGreaterThan(), isEqualTo());
    }

    private static Stream<Arguments> isLessThan() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(-1024), new BigDecimal("-512")),
                Arguments.of(BigDecimal.ONE.negate(), BigDecimal.ZERO),
                Arguments.of(BigDecimal.ZERO, BigDecimal.ONE),
                Arguments.of(new BigDecimal("0001"), BigDecimal.TEN),
                Arguments.of(BigDecimal.TEN, BigDecimal.valueOf(Byte.MAX_VALUE))
        );
    }

    private static Stream<Arguments> isLessThanOrEqualTo() {
        return Stream.concat(isLessThan(), isEqualTo());
    }

    private static Stream<Arguments> isPositive() {
        return Stream.of(
                Arguments.of(new BigDecimal("1")),
                Arguments.of(new BigDecimal("128")),
                Arguments.of(new BigDecimal("102.4")),
                Arguments.of(new BigDecimal("655.35")),
                Arguments.of(new BigDecimal("2147483.647")),
                Arguments.of(new BigDecimal("922337203685477.5807")),
                Arguments.of(new BigDecimal("87704359649718804783.29856"))
        );
    }

    private static Stream<Arguments> isZero() {
        return Stream.of(Arguments.of(BigDecimal.ZERO));
    }

    private static Stream<Arguments> isZeroOrPositive() {
        return Stream.concat(isZero(), isPositive());
    }

    private static Stream<Arguments> isNegative() {
        return Stream.of(
                Arguments.of(new BigDecimal("-1")),
                Arguments.of(new BigDecimal("-128")),
                Arguments.of(new BigDecimal("-102.4")),
                Arguments.of(new BigDecimal("-655.35")),
                Arguments.of(new BigDecimal("-2147483.647")),
                Arguments.of(new BigDecimal("-922337203685477.5807")),
                Arguments.of(new BigDecimal("-87704359649718804783.29856"))
        );
    }

    private static Stream<Arguments> isZeroOrNegative() {
        return Stream.concat(isZero(), isNegative());
    }

}

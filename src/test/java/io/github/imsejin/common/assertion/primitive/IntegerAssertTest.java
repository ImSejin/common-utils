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
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

@DisplayName("IntegerAssert")
class IntegerAssertTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.IntegerAssertTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.IntegerAssertTest#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(int actual, int expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(int actual, int expected) {
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
        void test0(int actual, int expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(int actual, int expected) {
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
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, (int) Character.valueOf('\u0000'));
            map.put(1024, -1024);
            map.put(Integer.valueOf(32), 31);
            map.put(Integer.MAX_VALUE, 1);
            map.put(-1, Integer.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Integer, Integer> map = new HashMap<>();
            map.put((int) Character.valueOf('\u0000'), 1);
            map.put(-1024, 1024);
            map.put(31, Integer.valueOf(31));
            map.put(1, Integer.MAX_VALUE);
            map.put(Integer.MIN_VALUE, -1);

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
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, (int) Character.valueOf('\u0000'));
            map.put(1024, -1024);
            map.put(Integer.valueOf(31), 31);
            map.put(Integer.MAX_VALUE, 1);
            map.put(-1, Integer.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Integer, Integer> map = new HashMap<>();
            map.put(-1, (int) Character.valueOf('\u0000'));
            map.put(-1024, 1024);
            map.put(31, Integer.valueOf(32));
            map.put(1, Integer.MAX_VALUE);
            map.put(Integer.MIN_VALUE, -1);

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
            Map<Integer, Integer> map = new HashMap<>();
            map.put((int) Character.valueOf('\u0000'), 1);
            map.put(-1024, 1024);
            map.put(31, Integer.valueOf(32));
            map.put(1, Integer.MAX_VALUE);
            map.put(Integer.MIN_VALUE, -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, (int) Character.valueOf('\u0000'));
            map.put(1024, -1024);
            map.put(Integer.valueOf(31), 31);
            map.put(Integer.MAX_VALUE, 1);
            map.put(-1, Integer.MIN_VALUE);

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
            Map<Integer, Integer> map = new HashMap<>();
            map.put((int) Character.valueOf('\u0000'), 1);
            map.put(-1024, 1024);
            map.put(31, Integer.valueOf(31));
            map.put(1, Integer.MAX_VALUE);
            map.put(Integer.MIN_VALUE, -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Integer, Integer> map = new HashMap<>();
            map.put((int) Character.valueOf('\u0000'), -1);
            map.put(1024, -1024);
            map.put(Integer.valueOf(32), 31);
            map.put(Integer.MAX_VALUE, 1);
            map.put(-1, Integer.MIN_VALUE);

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
        @ValueSource(ints = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(int actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(int actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(int actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(int actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(ints = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(int actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(int actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(ints = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(int actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(int actual) {
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
            IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1).limit(10_000)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, n + 1);
                        Asserts.that(n).isBetween(n - 1, n);
                        Asserts.that(n).isBetween(n - 1, n + 1);
                    }));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<Integer> integers = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).boxed().collect(toList());

            // except
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, n - 1)));
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 1, n)));
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n + 1, n - 1)));
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
                    .limit(10_000).forEach(n -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n - 1, n + 1)));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<Integer> integers = IntStream.rangeClosed(Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1)
                    .limit(10_000).boxed().collect(toList());

            // except
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n + 1)));
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n - 1, n)));
            integers.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n + 1, n - 1)));
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
                Asserts.that(Integer.MAX_VALUE).isCloseTo(Integer.MAX_VALUE, 0);
                Asserts.that(Integer.MAX_VALUE).isCloseTo((int) (Integer.MAX_VALUE * 0.25), 75.1);
                Asserts.that(123_456_789).isCloseTo(98_765, 99.93);
                Asserts.that(1024).isCloseTo(32, 96.875);
                Asserts.that(100).isCloseTo(93, 7.01);
                Asserts.that(64).isCloseTo(16, 75);
                Asserts.that(-5).isCloseTo(-4, 20);
                Asserts.that(-33).isCloseTo(-3, 90.91);
                Asserts.that(-500).isCloseTo(-499, 0.2);
                Asserts.that(-87_654_321).isCloseTo(-12_345, 99.986);
                Asserts.that(Integer.MIN_VALUE).isCloseTo((int) (Integer.MIN_VALUE * 0.25), 75);
                Asserts.that(Integer.MIN_VALUE).isCloseTo(Integer.MIN_VALUE, 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(36).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Integer.MAX_VALUE).isCloseTo((int) (Integer.MAX_VALUE * 0.25), 75))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Integer.MAX_VALUE).isCloseTo(Integer.MIN_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(64).isCloseTo(32, 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(1).isCloseTo(0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-1).isCloseTo(0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(-20).isCloseTo(-15, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Integer.MIN_VALUE).isCloseTo(Integer.MAX_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Integer.MIN_VALUE).isCloseTo((int) (Integer.MIN_VALUE * 0.9), 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0).isCloseTo(1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(0).isCloseTo(-1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put((int) Character.valueOf('\u0000'), 0);
        map.put(-1024, 1024 * -1);
        map.put(31, Integer.valueOf(31));
        map.put(Integer.MIN_VALUE, -2147483648);
        map.put(Integer.MAX_VALUE, 2147483647);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put((int) Character.valueOf('a'), 0);
        map.put(1024, -1024);
        map.put(31, 31 >> 7);
        map.put(Integer.MIN_VALUE, 2147483647);
        map.put(Integer.MAX_VALUE, -2147483648);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}

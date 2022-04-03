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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

@DisplayName("ShortAssert")
class ShortAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.lang.ShortAssertTest";
    private static final String EQUALITY = FQCN + "#equality";
    private static final String NON_EQUALITY = FQCN + "#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(short actual, short expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(short actual, short expected) {
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
        void test0(short actual, short expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(short actual, short expected) {
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
            Map<Short, Short> map = new HashMap<>();
            map.put((short) 1, (short) ((int) Character.valueOf('\u0000')));
            map.put((short) 1024, (short) -1024);
            map.put(Short.valueOf((short) 32), (short) 31);
            map.put(Short.MAX_VALUE, (short) 1);
            map.put((short) -1, Short.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Short, Short> map = new HashMap<>();
            map.put((short) ((int) Character.valueOf('\u0000')), (short) 1);
            map.put((short) -1024, (short) 1024);
            map.put((short) 31, Short.valueOf((short) 31));
            map.put((short) 1, Short.MAX_VALUE);
            map.put(Short.MIN_VALUE, (short) -1);

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
            Map<Short, Short> map = new HashMap<>();
            map.put((short) 1, (short) ((int) Character.valueOf('\u0000')));
            map.put((short) 1024, (short) -1024);
            map.put(Short.valueOf((short) 31), (short) 31);
            map.put(Short.MAX_VALUE, (short) 1);
            map.put((short) -1, Short.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Short, Short> map = new HashMap<>();
            map.put((short) -1, (short) ((int) Character.valueOf('\u0000')));
            map.put((short) -1024, (short) 1024);
            map.put((short) 31, Short.valueOf((short) 32));
            map.put((short) 1, Short.MAX_VALUE);
            map.put(Short.MIN_VALUE, (short) -1);

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
            Map<Short, Short> map = new HashMap<>();
            map.put((short) ((int) Character.valueOf('\u0000')), (short) 1);
            map.put((short) -1024, (short) 1024);
            map.put((short) 31, Short.valueOf((short) 32));
            map.put((short) 1, Short.MAX_VALUE);
            map.put(Short.MIN_VALUE, (short) -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Short, Short> map = new HashMap<>();
            map.put((short) 1, (short) ((int) Character.valueOf('\u0000')));
            map.put((short) 1024, (short) -1024);
            map.put(Short.valueOf((short) 31), (short) 31);
            map.put(Short.MAX_VALUE, (short) 1);
            map.put((short) -1, Short.MIN_VALUE);

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
            Map<Short, Short> map = new HashMap<>();
            map.put((short) ((int) Character.valueOf('\u0000')), (short) 1);
            map.put((short) -1024, (short) 1024);
            map.put((short) 31, Short.valueOf((short) 31));
            map.put((short) 1, Short.MAX_VALUE);
            map.put(Short.MIN_VALUE, (short) -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Short, Short> map = new HashMap<>();
            map.put((short) ((int) Character.valueOf('\u0000')), (short) -1);
            map.put((short) 1024, (short) -1024);
            map.put(Short.valueOf((short) 32), (short) 31);
            map.put(Short.MAX_VALUE, (short) 1);
            map.put((short) -1, Short.MIN_VALUE);

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
        @ValueSource(shorts = {1, Byte.MAX_VALUE, Short.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(short actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(shorts = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(short actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(shorts = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(short actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(shorts = {-1, Byte.MIN_VALUE, Short.MIN_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(short actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(shorts = {-1, Byte.MIN_VALUE, Short.MIN_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(short actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(shorts = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(short actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(shorts = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(short actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(shorts = {1, Byte.MAX_VALUE, Short.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(short actual) {
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
            IntStream.rangeClosed(Short.MIN_VALUE + 1, Short.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (short) n)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, (short) (n + 1));
                        Asserts.that(n).isBetween((short) (n - 1), n);
                        Asserts.that(n).isBetween((short) (n - 1), (short) (n + 1));
                    }));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<Short> shorts = IntStream.rangeClosed(Short.MIN_VALUE + 1, Short.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (short) n).collect(toList());

            // except
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, (short) (n - 1))));
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween((short) (n + 1), n)));
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween((short) (n + 1), (short) (n - 1))));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            IntStream.rangeClosed(Short.MIN_VALUE + 1, Short.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (short) n).forEach(n -> assertThatNoException()
                            .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((short) (n - 1), (short) (n + 1))));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<Short> shorts = IntStream.rangeClosed(Short.MIN_VALUE + 1, Short.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (short) n).collect(toList());

            // except
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, (short) (n + 1))));
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((short) (n - 1), n)));
            shorts.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((short) (n + 1), (short) (n - 1))));
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
                Asserts.that(Short.MAX_VALUE).isCloseTo(Short.MAX_VALUE, 0);
                Asserts.that(Short.MAX_VALUE).isCloseTo((short) (Short.MAX_VALUE * 0.25), 75.003);
                Asserts.that((short) 1024).isCloseTo((short) 32, 96.875);
                Asserts.that((short) 100).isCloseTo((short) 93, 7.01);
                Asserts.that((short) 64).isCloseTo((short) 16, 75);
                Asserts.that((short) -5).isCloseTo((short) -4, 20);
                Asserts.that((short) -33).isCloseTo((short) -3, 90.91);
                Asserts.that((short) -500).isCloseTo((short) -499, 0.2);
                Asserts.that(Short.MIN_VALUE).isCloseTo((short) (Short.MIN_VALUE * 0.25), 75);
                Asserts.that(Short.MIN_VALUE).isCloseTo(Short.MIN_VALUE, 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) 36).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Short.MAX_VALUE).isCloseTo((short) (Short.MAX_VALUE * 0.25), 75))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Short.MAX_VALUE).isCloseTo(Short.MIN_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) 64).isCloseTo((short) 32, 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) 1).isCloseTo((short) 0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) -1).isCloseTo((short) 0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) -20).isCloseTo((short) -15, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Short.MIN_VALUE).isCloseTo(Short.MAX_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Short.MIN_VALUE).isCloseTo((short) (Short.MIN_VALUE * 0.9), 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) 0).isCloseTo((short) 1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((short) 0).isCloseTo((short) -1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was ∞%.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Short, Short> map = new HashMap<>();
        map.put((short) ((int) Character.valueOf('\u0000')), (short) 0);
        map.put((short) -1024, (short) (1024 * -1));
        map.put((short) 31, Short.valueOf((short) 31));
        map.put(Short.MIN_VALUE, (short) -32768);
        map.put(Short.MAX_VALUE, (short) 32767);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Short, Short> map = new HashMap<>();
        map.put((short) ((int) Character.valueOf('a')), (short) 0);
        map.put((short) 1024, (short) -1024);
        map.put((short) 31, (short) (31 >> 7));
        map.put(Short.MIN_VALUE, (short) 32767);
        map.put(Short.MAX_VALUE, (short) -32768);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}

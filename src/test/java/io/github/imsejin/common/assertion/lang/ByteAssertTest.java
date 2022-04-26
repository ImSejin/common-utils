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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ByteAssert")
class ByteAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.lang.ByteAssertTest";
    private static final String EQUALITY = FQCN + "#equality";
    private static final String NON_EQUALITY = FQCN + "#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(byte actual, byte expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(byte actual, byte expected) {
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
        void test0(byte actual, byte expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(byte actual, byte expected) {
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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 32), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 0);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 31));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 31), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) -1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 32));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 1);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 32));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 31), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 1);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 31));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

            // except
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) -1);
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 32), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

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
        @ValueSource(bytes = {1, Byte.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(bytes = {0, -1, Byte.MIN_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(bytes = {0, 1, Byte.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(bytes = {-1, Byte.MIN_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(bytes = {-1, Byte.MIN_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(bytes = {0, 1, Byte.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(bytes = {0, -1, Byte.MIN_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(byte actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(bytes = {1, Byte.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(byte actual) {
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
            IntStream.rangeClosed(Byte.MIN_VALUE + 1, Byte.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (byte) n)
                    .forEach(n -> assertThatNoException().isThrownBy(() -> {
                        Asserts.that(n).isBetween(n, n);
                        Asserts.that(n).isBetween(n, (byte) (n + 1));
                        Asserts.that(n).isBetween((byte) (n - 1), n);
                        Asserts.that(n).isBetween((byte) (n - 1), (byte) (n + 1));
                    }));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y inclusively")
        void test1() {
            // given
            List<Byte> bytes = IntStream.rangeClosed(Byte.MIN_VALUE + 1, Byte.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (byte) n).collect(toList());

            // except
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween(n, (byte) (n - 1))));
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween((byte) (n + 1), n)));
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isBetween((byte) (n + 1), (byte) (n - 1))));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'IsStrictlyBetween'")
    class IsStrictlyBetween {
        @Test
        @DisplayName("passes, when actual is between x and y exclusively")
        void test0() {
            IntStream.rangeClosed(Byte.MIN_VALUE + 1, Byte.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (byte) n).forEach(n -> assertThatNoException()
                            .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((byte) (n - 1), (byte) (n + 1))));
        }

        @Test
        @DisplayName("throws exception, when actual is not between x and y exclusively")
        void test1() {
            // given
            List<Byte> bytes = IntStream.rangeClosed(Byte.MIN_VALUE + 1, Byte.MAX_VALUE - 1)
                    .limit(10_000).mapToObj(n -> (byte) n).collect(toList());

            // except
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, n)));
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween(n, (byte) (n + 1))));
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((byte) (n - 1), n)));
            bytes.forEach(n -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(n).isStrictlyBetween((byte) (n + 1), (byte) (n - 1))));
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
                Asserts.that(Byte.MAX_VALUE).isCloseTo(Byte.MAX_VALUE, 0);
                Asserts.that(Byte.MAX_VALUE).isCloseTo((byte) (Byte.MAX_VALUE * 0.25), 75.6);
                Asserts.that((byte) 100).isCloseTo((byte) 93, 7.01);
                Asserts.that((byte) 64).isCloseTo((byte) 16, 75);
                Asserts.that((byte) -5).isCloseTo((byte) -4, 20);
                Asserts.that((byte) -33).isCloseTo((byte) -3, 90.91);
                Asserts.that(Byte.MIN_VALUE).isCloseTo((byte) (Byte.MIN_VALUE * 0.25), 75);
                Asserts.that(Byte.MIN_VALUE).isCloseTo(Byte.MIN_VALUE, 0);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not close to other")
        void test1() {
            String regex = "^It is expected to close to other by less than [0-9.]+%, but difference was -?[0-9.]+%\\..+";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) 36).isCloseTo(null, 15))
                    .withMessageStartingWith("It is expected to close to other, but it isn't.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Byte.MAX_VALUE).isCloseTo((byte) (Byte.MAX_VALUE * 0.25), 75))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Byte.MAX_VALUE).isCloseTo(Byte.MIN_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) 64).isCloseTo((byte) 32, 49.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) 1).isCloseTo((byte) 0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) -1).isCloseTo((byte) 0, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) -20).isCloseTo((byte) -15, 10))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Byte.MIN_VALUE).isCloseTo(Byte.MAX_VALUE, 99.9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(Byte.MIN_VALUE).isCloseTo((byte) (Byte.MIN_VALUE * 0.9), 9))
                    .withMessageMatching(regex);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) 0).isCloseTo((byte) 1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that((byte) 0).isCloseTo((byte) -1, 99.9))
                    .withMessageStartingWith("It is expected to close to other by less than 99.9%, but difference was Infinity%.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Byte, Byte> map = new HashMap<>();
        map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 0);
        map.put((byte) -124, (byte) (124 * -1));
        map.put((byte) 31, Byte.valueOf((byte) 31));
        map.put(Byte.MIN_VALUE, (byte) -128);
        map.put(Byte.MAX_VALUE, (byte) 127);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Byte, Byte> map = new HashMap<>();
        map.put((byte) ((int) Character.valueOf('a')), (byte) 0);
        map.put((byte) 124, (byte) -124);
        map.put((byte) 31, (byte) (31 >> 7));
        map.put(Byte.MIN_VALUE, (byte) 127);
        map.put(Byte.MAX_VALUE, (byte) -128);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}

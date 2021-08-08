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

@DisplayName("ByteAssert")
class ByteAssertTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.ByteAssertTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.ByteAssertTest#nonEquality";

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 32), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 0);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 31));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 31), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) -1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 32));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 1);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 32));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) 1, (byte) ((int) Character.valueOf('\u0000')));
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 31), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

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
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) 1);
            map.put((byte) -124, (byte) 124);
            map.put((byte) 31, Byte.valueOf((byte) 31));
            map.put((byte) 1, Byte.MAX_VALUE);
            map.put(Byte.MIN_VALUE, (byte) -1);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            Map<Byte, Byte> map = new HashMap<>();
            map.put((byte) ((int) Character.valueOf('\u0000')), (byte) -1);
            map.put((byte) 124, (byte) -124);
            map.put(Byte.valueOf((byte) 32), (byte) 31);
            map.put(Byte.MAX_VALUE, (byte) 1);
            map.put((byte) -1, Byte.MIN_VALUE);

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

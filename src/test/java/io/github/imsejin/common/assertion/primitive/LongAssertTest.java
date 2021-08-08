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

@DisplayName("LongAssert")
class LongAssertTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.LongAssertTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.LongAssertTest#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when actual is equal to other")
        void test0(long actual, long expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(long actual, long expected) {
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
        void test0(long actual, long expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(long actual, long expected) {
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
            Map<Long, Long> map = new HashMap<>();
            map.put(1L, (long) Character.valueOf('\u0000'));
            map.put(1024L, -1024L);
            map.put(Long.valueOf(32), 31L);
            map.put(Long.MAX_VALUE, 1L);
            map.put(-1L, Long.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put((long) Character.valueOf('\u0000'), 0L);
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(31));
            map.put(1L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, -1L);

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
            Map<Long, Long> map = new HashMap<>();
            map.put(1L, (long) Character.valueOf('\u0000'));
            map.put(1024L, -1024L);
            map.put(Long.valueOf(31), 31L);
            map.put(Long.MAX_VALUE, 1L);
            map.put(-1L, Long.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put(-1L, (long) Character.valueOf('\u0000'));
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(32));
            map.put(1L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, -1L);

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
            Map<Long, Long> map = new HashMap<>();
            map.put((long) Character.valueOf('\u0000'), 1L);
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(32));
            map.put(1L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, -1L);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put(1L, (long) Character.valueOf('\u0000'));
            map.put(1024L, -1024L);
            map.put(Long.valueOf(31), 31L);
            map.put(Long.MAX_VALUE, 1L);
            map.put(-1L, Long.MIN_VALUE);

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
            Map<Long, Long> map = new HashMap<>();
            map.put((long) Character.valueOf('\u0000'), 1L);
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(31));
            map.put(1L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, -1L);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put((long) Character.valueOf('\u0000'), -1L);
            map.put(1024L, -1024L);
            map.put(Long.valueOf(32), 31L);
            map.put(Long.MAX_VALUE, 1L);
            map.put(-1L, Long.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(longs = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE})
        @DisplayName("passes, when actual is positive")
        void test0(long actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(longs = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Long.MIN_VALUE})
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(long actual) {
            assertThatCode(() -> Asserts.that(actual).isPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(longs = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE})
        @DisplayName("passes, when actual is zero or positive")
        void test0(long actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(longs = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Long.MIN_VALUE})
        @DisplayName("throws exception, when actual is negative")
        void test1(long actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrPositive())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(longs = {-1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Long.MIN_VALUE})
        @DisplayName("passes, when actual is negative")
        void test0(long actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(longs = {0, 1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE})
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(long actual) {
            assertThatCode(() -> Asserts.that(actual).isNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(longs = {0, -1, Byte.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Long.MIN_VALUE})
        @DisplayName("passes, when actual is zero or negative")
        void test0(long actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(longs = {1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE})
        @DisplayName("throws exception, when actual is positive")
        void test1(long actual) {
            assertThatCode(() -> Asserts.that(actual).isZeroOrNegative())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Long, Long> map = new HashMap<>();
        map.put((long) Character.valueOf('\u0000'), 0L);
        map.put(-1024L, 1024 * -1L);
        map.put(31L, Long.valueOf(31));
        map.put(Long.MIN_VALUE, -9223372036854775808L);
        map.put(Long.MAX_VALUE, 9223372036854775807L);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Long, Long> map = new HashMap<>();
        map.put((long) Character.valueOf('a'), 0L);
        map.put(1024L, -1024L);
        map.put(31L, 31L >> 7);
        map.put(Long.MIN_VALUE, 9223372036854775807L);
        map.put(Long.MAX_VALUE, -9223372036854775808L);

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}

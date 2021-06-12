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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("LongAsserts")
class LongAssertsTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.LongAssertsTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.LongAssertsTest#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when target is equal to other")
        void test0(long actual, long expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when target is not equal to other")
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
        @DisplayName("passes, when target is not equal to other")
        void test0(long actual, long expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when target is equal to other")
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
        @DisplayName("passes, when target is greater than other")
        void test0() {
            Map<Long, Long> map = new HashMap<>();
            map.put(1L, (long) Character.valueOf('\u0000'));
            map.put(1024L, -1024L);
            map.put(Long.valueOf(32), 31L);
            map.put(Long.MAX_VALUE, 0L);
            map.put(0L, Long.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is less than or equal to other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put((long) Character.valueOf('\u0000'), 0L);
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(31));
            map.put(0L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, 0L);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when target is greater than or equal to other")
        void test0() {
            Map<Long, Long> map = new HashMap<>();
            map.put(1L, (long) Character.valueOf('\u0000'));
            map.put(1024L, -1024L);
            map.put(Long.valueOf(31), 31L);
            map.put(Long.MAX_VALUE, 0L);
            map.put(0L, Long.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is less than other")
        void test1() {
            Map<Long, Long> map = new HashMap<>();
            map.put(-1L, (long) Character.valueOf('\u0000'));
            map.put(-1024L, 1024L);
            map.put(31L, Long.valueOf(32));
            map.put(0L, Long.MAX_VALUE);
            map.put(Long.MIN_VALUE, 0L);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
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

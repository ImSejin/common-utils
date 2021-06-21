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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("CharacterAsserts")
class CharacterAssertsTest {

    private static final String EQUALITY = "io.github.imsejin.common.assertion.primitive.CharacterAssertsTest#equality";
    private static final String NON_EQUALITY = "io.github.imsejin.common.assertion.primitive.CharacterAssertsTest#nonEquality";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("passes, when target is equal to other")
        void test0(int actual, int expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when target is not equal to other")
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
        @DisplayName("passes, when target is not equal to other")
        void test0(int actual, int expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when target is equal to other")
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
        @DisplayName("passes, when target is greater than other")
        void test0() {
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, Character.valueOf('\u0000'));
            map.put((char) 1024, (char) 512);
            map.put(Character.valueOf((char) 32), (char) 31);
            map.put(Character.MAX_VALUE, (char) 1);
            map.put('\u0001', Character.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is less than or equal to other")
        void test1() {
            Map<Character, Character> map = new HashMap<>();
            map.put(Character.valueOf('\u0000'), (char) 1);
            map.put((char) 512, (char) 1024);
            map.put((char) 31, Character.valueOf((char) 31));
            map.put((char) 1, Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0000');

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
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, Character.valueOf('\u0000'));
            map.put((char) 1024, (char) 512);
            map.put(Character.valueOf((char) 31), (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is less than other")
        void test1() {
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', Character.valueOf('\u0003'));
            map.put((char) 512, (char) 1024);
            map.put((char) 31, Character.valueOf((char) 32));
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when target is less than other")
        void test0() {
            Map<Character, Character> map = new HashMap<>();
            map.put(Character.valueOf('\u0002'), '\u0003');
            map.put((char) 512, (char) 1024);
            map.put((char) 31, Character.valueOf((char) 32));
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is greater than or equal to other")
        void test1() {
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, Character.valueOf('\u0000'));
            map.put((char) 1024, (char) 512);
            map.put(Character.valueOf((char) 31), (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when target is less than or equal to other")
        void test0() {
            Map<Character, Character> map = new HashMap<>();
            map.put(Character.valueOf('\u0002'), '\u0003');
            map.put((char) 512, (char) 1024);
            map.put((char) 31, Character.valueOf((char) 31));
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is greater than other")
        void test1() {
            Map<Character, Character> map = new HashMap<>();
            map.put(Character.valueOf('\u0002'), '\u0000');
            map.put((char) 1024, (char) 512);
            map.put(Character.valueOf((char) 32), (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZero'")
    class IsZero {
        @Test
        @DisplayName("passes, when target is null-character")
        void test0() {
            List<Character> characters = Arrays.asList('\u0000', (char) 0);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isZero())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is not null-character")
        void test1() {
            List<Character> characters = Arrays.asList('\u0001', '0', 'a', null);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isZero())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotZero'")
    class IsNotZero {
        @Test
        @DisplayName("passes, when target is null-character")
        void test0() {
            List<Character> characters = Arrays.asList('\u0001', '0', 'a', null);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNotZero())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when target is not null-character")
        void test1() {
            List<Character> characters = Arrays.asList('\u0000', (char) 0);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNotZero())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Character, Character> map = new HashMap<>();
        map.put(Character.valueOf('\u0000'), '\u0000');
        map.put((char) 1024, (char) (512 * 2));
        map.put((char) 31, Character.valueOf((char) 31));
        map.put(Character.MIN_VALUE, '\u0000');
        map.put(Character.MAX_VALUE, '\uFFFF');

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> nonEquality() {
        Map<Character, Character> map = new HashMap<>();
        map.put(Character.valueOf('a'), '\u0001');
        map.put((char) 1024, (char) (512 / 2));
        map.put((char) 31, (char) (31 >> 7));
        map.put(Character.MIN_VALUE, '\uFFFF');
        map.put(Character.MAX_VALUE, '\u0000');

        return map.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

}
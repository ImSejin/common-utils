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

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
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
        @DisplayName("passes, when actual is equal to other")
        void test0(char actual, char expected) {
            assertThatCode(() -> Asserts.that(actual).isEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(NON_EQUALITY)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(char actual, char expected) {
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
        void test0(char actual, char expected) {
            assertThatCode(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource(EQUALITY)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(char actual, char expected) {
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
        @DisplayName("throws exception, when actual is less than or equal to other")
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
        @DisplayName("passes, when actual is greater than or equal to other")
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
        @DisplayName("throws exception, when actual is less than other")
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
        @DisplayName("passes, when actual is less than other")
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
        @DisplayName("throws exception, when actual is greater than or equal to other")
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
        @DisplayName("passes, when actual is less than or equal to other")
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
        @DisplayName("throws exception, when actual is greater than other")
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
        @DisplayName("passes, when actual is null-character")
        void test0() {
            List<Character> characters = Arrays.asList('\u0000', (char) 0);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isZero())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not null-character")
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
        @DisplayName("passes, when actual is null-character")
        void test0() {
            List<Character> characters = Arrays.asList('\u0001', '0', 'a', null);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNotZero())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not null-character")
        void test1() {
            List<Character> characters = Arrays.asList('\u0000', (char) 0);

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNotZero())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isDigit'")
    class IsDigit {
        @ParameterizedTest
        @ValueSource(chars = {
                // ISO-LATIN-1 digits
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                // Arabic-Indic digits
                '\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669',
                // Extended Arabic-Indic digits
                '\u06F0', '\u06F1', '\u06F2', '\u06F3', '\u06F4', '\u06F5', '\u06F6', '\u06F7', '\u06F8', '\u06F9',
                // Devanagari digits
                '\u0966', '\u0967', '\u0968', '\u0969', '\u096A', '\u096B', '\u096C', '\u096D', '\u096E', '\u096F',
                // Fullwidth digits
                '\uFF10', '\uFF11', '\uFF12', '\uFF13', '\uFF14', '\uFF15', '\uFF16', '\uFF17', '\uFF18', '\uFF19',
        })
        @DisplayName("passes, when actual is digit")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isDigit())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not digit")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isDigit(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isDigit())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLetter'")
    class IsLetter {
        @Test
        @DisplayName("passes, when actual is letter")
        void test0() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isLetter).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetter())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not letter")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isLetter(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetter())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLetterOrDigit'")
    class IsLetterOrDigit {
        @Test
        @DisplayName("passes, when actual is letter or digit")
        void test0() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isLetterOrDigit).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetterOrDigit())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is letter nor digit")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isLetterOrDigit(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetterOrDigit())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isUpperCase'")
    class IsUpperCase {
        @ParameterizedTest
        @ValueSource(chars = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        })
        @DisplayName("passes, when actual is uppercase")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isUpperCase())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not uppercase")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isUpperCase(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isUpperCase())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLowerCase'")
    class IsLowerCase {
        @ParameterizedTest
        @ValueSource(chars = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        })
        @DisplayName("passes, when actual is lowercase")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isLowerCase())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not lowercase")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isLowerCase(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLowerCase())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAlphabetic'")
    class IsAlphabetic {
        @Test
        @DisplayName("passes, when actual is alphabetic")
        void test0() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isAlphabetic).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isAlphabetic())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not alphabetic")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isAlphabetic(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isAlphabetic())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSpaceChar'")
    class IsSpaceChar {
        @ParameterizedTest
        @ValueSource(chars = {'\u00A0', '\u2007', '\u202F'})
        @DisplayName("passes, when actual is space character")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isSpaceChar())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not space character")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isSpaceChar(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isSpaceChar())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isWhitespace'")
    class IsWhitespace {
        @ParameterizedTest
        @ValueSource(chars = {
                '\t', '\n', '\u000B', '\f', '\r',
                '\u001C', '\u001D', '\u001E', '\u001F',
        })
        @DisplayName("passes, when actual is whitespace character")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isWhitespace())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not whitespace character")
        void test1() {
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isWhitespace(it)).collect(toList());

            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isWhitespace())
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> equality() {
        Map<Character, Character> map = new HashMap<>();
        map.put(Character.valueOf('\u0001'), '\u0001');
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

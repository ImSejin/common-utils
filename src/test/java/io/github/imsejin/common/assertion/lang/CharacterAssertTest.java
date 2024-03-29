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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("CharacterAssert")
class CharacterAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            // given
            Map<Character, Character> params = new HashMap<>();
            params.put('\u0000', Character.MIN_VALUE);
            params.put('\u0020', ' ');
            params.put('0', (char) 48);
            params.put(Character.MAX_VALUE, '\uffff');
            params.put((char) 122, 'z');

            // expect
            params.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual)
                    .isEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            // given
            Map<Character, Character> params = new HashMap<>();
            params.put('a', '\u0001');
            params.put((char) 1024, (char) (512 / 2));
            params.put((char) 31, (char) (31 >> 7));
            params.put(Character.MIN_VALUE, '\uffff');
            params.put(Character.MAX_VALUE, '\u0000');

            // expect
            params.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual)
                    .isEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @Test
        @DisplayName("passes, when actual is not equal to other")
        void test0() {
            // given
            Map<Character, Character> params = new HashMap<>();
            params.put('a', '\u0001');
            params.put((char) 1024, (char) (512 / 2));
            params.put((char) 31, (char) (31 >> 7));
            params.put(Character.MIN_VALUE, '\uffff');
            params.put(Character.MAX_VALUE, '\u0000');

            // expect
            params.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual)
                    .isNotEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            // given
            Map<Character, Character> params = new HashMap<>();
            params.put('\u0000', Character.MIN_VALUE);
            params.put('\u0020', ' ');
            params.put('0', (char) 48);
            params.put(Character.MAX_VALUE, '\uffff');
            params.put((char) 122, 'z');

            // expect
            params.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual)
                    .isNotEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @Test
        @DisplayName("passes, when actual is greater than other")
        void test0() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, Character.MIN_VALUE);
            map.put((char) 1024, (char) 512);
            map.put((char) 32, (char) 31);
            map.put(Character.MAX_VALUE, (char) 1);
            map.put('\u0001', Character.MIN_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', (char) 3);
            map.put((char) 512, (char) 1024);
            map.put((char) 31, (char) 31);
            map.put((char) 1, Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0000');

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("It is expected to be greater than the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, '\u0000');
            map.put((char) 1024, (char) 512);
            map.put((char) 31, (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            // expect
            map.forEach(
                    (actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                            .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is less than other")
        void test1() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', '\u0003');
            map.put((char) 512, (char) 1024);
            map.put((char) 31, (char) 32);
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            // expect
            map.forEach(
                    (actual, expected) -> assertThatCode(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected))
                            .isExactlyInstanceOf(IllegalArgumentException.class)
                            .hasMessageStartingWith(
                                    "It is expected to be greater than or equal to the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @Test
        @DisplayName("passes, when actual is less than other")
        void test0() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', '\u0003');
            map.put((char) 512, (char) 1024);
            map.put((char) 31, (char) 32);
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put((char) 2, '\u0000');
            map.put((char) 1024, (char) 512);
            map.put((char) 31, (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThan(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("It is expected to be less than the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @Test
        @DisplayName("passes, when actual is less than or equal to other")
        void test0() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', '\u0003');
            map.put((char) 512, (char) 1024);
            map.put((char) 31, (char) 31);
            map.put('\u0001', Character.MAX_VALUE);
            map.put(Character.MIN_VALUE, '\u0001');

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is greater than other")
        void test1() {
            // given
            Map<Character, Character> map = new HashMap<>();
            map.put('\u0002', '\u0000');
            map.put((char) 1024, (char) 512);
            map.put((char) 32, (char) 31);
            map.put(Character.MAX_VALUE, '\u0001');
            map.put('\u0001', Character.MIN_VALUE);

            // expect
            map.forEach((actual, expected) -> assertThatCode(() -> Asserts.that(actual).isLessThanOrEqualTo(expected))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("It is expected to be less than or equal to the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

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
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isDigit(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isDigit())
                    .withMessageMatching(Pattern.quote("It is expected to be digit, but it isn't.") +
                            "\n {4}actual: '[^0-9\u0660-\u0669\u06F0-\u06F9\u0966-\u096F\uFF10-\uFF19]'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLetter'")
    class IsLetter {
        @Test
        @DisplayName("passes, when actual is letter")
        void test0() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isLetter).collect(toList());

            // expect
            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetter())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not letter")
        void test1() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isLetter(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isLetter())
                    .withMessageMatching(Pattern.quote("It is expected to be letter, but it isn't.") +
                            "\n {4}actual: '[\\s\\S]'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLetterOrDigit'")
    class IsLetterOrDigit {
        @Test
        @DisplayName("passes, when actual is letter or digit")
        void test0() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isLetterOrDigit).collect(toList());

            // expect
            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isLetterOrDigit())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is letter nor digit")
        void test1() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isLetterOrDigit(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isLetterOrDigit())
                    .withMessageMatching(Pattern.quote("It is expected to be letter or digit, but it isn't.") +
                            "\n {4}actual: '[\\s\\S]'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isUpperCase'")
    class IsUpperCase {
        @ParameterizedTest
        @ValueSource(chars = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '\u00C0', '\u00C1', '\u00C2', '\u00C3', '\u00C4', '\u00C5', '\u00C6', '\u00C7',
                '\u00C8', '\u00C9', '\u00CA', '\u00CB', '\u00CC', '\u00CD', '\u00CE', '\u00CF',
                '\u00D0', '\u00D1', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00D6', '\u00D8',
                '\u00D9', '\u00DA', '\u00DB', '\u00DC', '\u00DD', '\u00DE',
        })
        @DisplayName("passes, when actual is uppercase")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isUpperCase())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(chars = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '\u00DF', '\u00E0', '\u00E1', '\u00E2', '\u00E3', '\u00E4', '\u00E5', '\u00E6',
                '\u00E7', '\u00E8', '\u00E9', '\u00EA', '\u00EB', '\u00EC', '\u00ED', '\u00EE',
                '\u00EF', '\u00F0', '\u00F1', '\u00F2', '\u00F3', '\u00F4', '\u00F5', '\u00F6',
                '\u00F8', '\u00F9', '\u00FA', '\u00FB', '\u00FC', '\u00FD', '\u00FE', '\u00FF'
        })
        @DisplayName("throws exception, when actual is not uppercase")
        void test1(char actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isUpperCase())
                    .withMessageMatching(Pattern.quote("It is expected to be upper case, but it isn't.") +
                            "\n {4}actual: '[^A-Z\u00C0-\u00DE]'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLowerCase'")
    class IsLowerCase {
        @ParameterizedTest
        @ValueSource(chars = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '\u00DF', '\u00E0', '\u00E1', '\u00E2', '\u00E3', '\u00E4', '\u00E5', '\u00E6',
                '\u00E7', '\u00E8', '\u00E9', '\u00EA', '\u00EB', '\u00EC', '\u00ED', '\u00EE',
                '\u00EF', '\u00F0', '\u00F1', '\u00F2', '\u00F3', '\u00F4', '\u00F5', '\u00F6',
                '\u00F8', '\u00F9', '\u00FA', '\u00FB', '\u00FC', '\u00FD', '\u00FE', '\u00FF'
        })
        @DisplayName("passes, when actual is lowercase")
        void test0(char actual) {
            assertThatCode(() -> Asserts.that(actual).isLowerCase())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(chars = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '\u00C0', '\u00C1', '\u00C2', '\u00C3', '\u00C4', '\u00C5', '\u00C6', '\u00C7',
                '\u00C8', '\u00C9', '\u00CA', '\u00CB', '\u00CC', '\u00CD', '\u00CE', '\u00CF',
                '\u00D0', '\u00D1', '\u00D2', '\u00D3', '\u00D4', '\u00D5', '\u00D6', '\u00D8',
                '\u00D9', '\u00DA', '\u00DB', '\u00DC', '\u00DD', '\u00DE',
        })
        @DisplayName("throws exception, when actual is not lowercase")
        void test1(char actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isLowerCase())
                    .withMessageMatching(Pattern.quote("It is expected to be lower case, but it isn't.") +
                            "\n {4}actual: '[^a-z\u00DF-\u00FF]'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAlphabetic'")
    class IsAlphabetic {
        @Test
        @DisplayName("passes, when actual is alphabetic")
        void test0() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(Character::isAlphabetic).collect(toList());

            // expect
            characters.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isAlphabetic())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is not alphabetic")
        void test1() {
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isAlphabetic(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isAlphabetic())
                    .withMessageMatching(Pattern.quote("It is expected to be alphabetic, but it isn't.") +
                            "\n {4}actual: '[\\s\\S]'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

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
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isSpaceChar(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isSpaceChar())
                    .withMessageMatching(Pattern.quote("It is expected to be space character, but it isn't.") +
                            "\n {4}actual: '[\\s\\S]'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isWhitespace'")
    class IsWhitespace {
        @ParameterizedTest
        @ValueSource(chars = {
                '\t', '\n', '\u000B', '\f', '\r', '\u0020',
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
            // given
            List<Character> characters = new Random().ints(50, 0, Character.MAX_VALUE + 1).mapToObj(n -> (char) n)
                    .filter(it -> !Character.isWhitespace(it)).collect(toList());

            // expect
            characters.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isWhitespace())
                    .withMessageMatching(Pattern.quote("It is expected to be whitespace, but it isn't.") +
                            "\n {4}actual: '\\S'"));
        }
    }

}

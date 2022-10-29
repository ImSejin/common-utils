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
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("StringAssert")
class StringAssertTest {

    @Nested
    @DisplayName("method 'hasText'")
    class HasText {
        @ParameterizedTest
        @ValueSource(strings = {"io", "github", "Im Sejin", "common-utils", "assertion", "chars"})
        @DisplayName("passes, when actual has text")
        void test0(String actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).hasText());
        }

        @ParameterizedTest
        @ValueSource(chars = {
                '\t', '\n', '\u000B', '\f', '\r', '\u0020',
                '\u001C', '\u001D', '\u001E', '\u001F',
        })
        @DisplayName("throws exception, when actual doesn't have text")
        void test1(char actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(String.valueOf(actual))
                    .hasText())
                    .withMessageMatching(Pattern.quote("It is expected to have text, but it isn't.") +
                            "\n {4}actual: '(.|\\s)'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNumeric'")
    class IsNumeric {
        @RepeatedTest(10)
        @DisplayName("passes, when actual is numeric")
        void test0() {
            // given
            String actual = UUID.randomUUID().toString().replaceAll("[^\\d]", "");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNumeric());
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "0 1", "value-1", "2000-01-01", "imsejin"})
        @DisplayName("throws exception, when actual is not numeric")
        void test1(String actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNumeric())
                    .withMessageMatching(Pattern.quote("It is expected to be numeric, but it isn't.") +
                            "\n {4}actual: '.*'");
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
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .filter(Character::isLetter)
                    .mapToObj(n -> String.valueOf((char) n))
                    .collect(toList());

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isLetter()));
        }

        @Test
        @DisplayName("throws exception, when actual is not letter")
        void test1() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .mapToObj(n -> (Character.isLetter(n) ? "-" : "") + (char) n)
                    .collect(toList());
            list.add("");

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLetter())
                    .withMessageMatching(Pattern.quote("It is expected to be letter, but it isn't.") +
                            "\n {4}actual: '(\\s|\\S)*'"));
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
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .filter(Character::isLetterOrDigit)
                    .mapToObj(n -> String.valueOf((char) n))
                    .collect(toList());

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isLetterOrDigit()));
        }

        @Test
        @DisplayName("throws exception, when actual is not letter and digit")
        void test1() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .mapToObj(n -> (Character.isLetterOrDigit(n) ? "-" : "") + (char) n)
                    .collect(toList());
            list.add("");

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLetterOrDigit())
                    .withMessageMatching(Pattern.quote("It is expected to be letter or digit, but it isn't.") +
                            "\n {4}actual: '(\\s|\\S)*'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isUpperCase'")
    class IsUpperCase {
        @Test
        @DisplayName("passes, when actual has only uppercase letters")
        void test0() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .filter(Character::isUpperCase)
                    .mapToObj(n -> String.valueOf((char) n))
                    .collect(toList());

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isUpperCase()));
        }

        @Test
        @DisplayName("throws exception, when actual has a lowercase letter")
        void test1() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .mapToObj(n -> (Character.isUpperCase(n) ? "a" : "") + (char) n)
                    .collect(toList());
            list.add("");

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isUpperCase())
                    .withMessageMatching(Pattern.quote("It is expected to have only uppercase letter(s), but it isn't.") +
                            "\n {4}actual: '(\\s|\\S)*'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLowerCase'")
    class IsLowerCase {
        @Test
        @DisplayName("passes, when actual has only lowercase letters")
        void test0() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .filter(Character::isLowerCase)
                    .mapToObj(n -> String.valueOf((char) n))
                    .collect(toList());

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isLowerCase()));
        }

        @Test
        @DisplayName("throws exception, when actual has a uppercase letter")
        void test1() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .mapToObj(n -> (Character.isLowerCase(n) ? "A" : "") + (char) n)
                    .collect(toList());
            list.add("");

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLowerCase())
                    .withMessageMatching(Pattern.quote("It is expected to have only lowercase letter(s), but it isn't.") +
                            "\n {4}actual: '(\\s|\\S)*'"));
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
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .filter(Character::isAlphabetic)
                    .mapToObj(n -> String.valueOf((char) n))
                    .collect(toList());

            // expect
            list.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isAlphabetic()));
        }

        @Test
        @DisplayName("throws exception, when actual is not alphabetic")
        void test1() {
            // given
            List<String> list = IntStream.rangeClosed(0, Character.MAX_VALUE)
                    .mapToObj(n -> (Character.isAlphabetic(n) ? "_" : "") + (char) n)
                    .collect(toList());
            list.add("");

            // expect
            list.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAlphabetic())
                    .withMessageMatching(Pattern.quote("It is expected to be alphabetic, but it isn't.") +
                            "\n {4}actual: '(\\s|\\S)*'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'startsWith'")
    class StartsWith {
        @ParameterizedTest
        @CsvSource(value = {
                "io.github.imsejin.common.assertion.chars                           | io.github.imsejin.common",
                "Lorem Ipsum is simply dummy text                                   | Lorem Ipsum",
                "It is a long established fact that a reader will be distracted     | It ",
                "Contrary to popular belief; Lorem Ipsum is not simply random text. | C",
                "There are many variations of passages of Lorem Ipsum available     | There are",
        }, delimiter = '|')
        @DisplayName("passes, when actual starts with the given string")
        void test0(String actual, String expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).startsWith(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "io.github.imsejin.common.assertion.chars                           | o.github.imsejin.common",
                "Lorem Ipsum is simply dummy text                                   | ",
                "It is a long established fact that a reader will be distracted     | it is",
                "Contrary to popular belief; Lorem Ipsum is not simply random text. | const",
                "There are many variations of passages of Lorem Ipsum available     | THERE IS",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't start with the given string")
        void test1(String actual, String expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .startsWith(expected))
                    .withMessageMatching(Pattern.quote("It is expected to start with the given string, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}expected: '.*'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'endsWith'")
    class EndsWith {
        @ParameterizedTest
        @CsvSource(value = {
                "io.github.imsejin.common.assertion.chars                           | common.assertion.chars",
                "Lorem Ipsum is simply dummy text                                   | simply dummy text",
                "It is a long established fact that a reader will be distracted     | distracted",
                "Contrary to popular belief; Lorem Ipsum is not simply random text. | .",
                "There are many variations of passages of Lorem Ipsum available     | available",
        }, delimiter = '|')
        @DisplayName("passes, when actual ends with the given string")
        void test0(String actual, String expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).endsWith(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "io.github.imsejin.common.assertion.chars                           | common.assertion.char",
                "Lorem Ipsum is simply dummy text                                   | ",
                "It is a long established fact that a reader will be distracted     | be  distracted",
                "Contrary to popular belief; Lorem Ipsum is not simply random text. | Text.",
                "There are many variations of passages of Lorem Ipsum available     | AVAILABLE",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't end with the given string")
        void test1(String actual, String expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .endsWith(expected))
                    .withMessageMatching(Pattern.quote("It is expected to end with the given string, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}expected: '.*'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'matches'")
    class Matches {
        @ParameterizedTest
        @CsvSource(value = {
                "843e4377-4222-416f-96dd-badfe606baae ; ^[a-z\\d]{8}-[a-z\\d]{4}-[a-z\\d]{4}-[a-z\\d]{4}-[a-z\\d]{12}$",
                "991231-1234567                       ; ^\\d{2}(?:0[1-9]|1[0-2])(?:[0-2]\\d|3[0-1])-[1-4]\\d{6}$",
                "sample@paper.com                     ; ^[\\w-]+(?:.[\\w-]+)*@(?:\\w+\\.)+\\w+$",
                "010-123-4567                         ; ^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
        }, delimiter = ';')
        @DisplayName("passes, when actual matches the given regular expression")
        void test0(String actual, @Language("RegExp") String expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).matches(expected));
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).matches(Pattern.compile(expected)));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "843e4377-4222-416f-96dd-badfe606baae ; ^\\d{8}-\\d{4}-\\d{4}-\\d{4}-\\d{12}$",
                "991301-1234567                       ; \\d{2}(?:0[1-9]|1[0-2])(?:[0-2]\\d|3[0-1])-[1-4]\\d{6}",
                "sample-company@paper.com             ; ^\\w+@(?:\\w+\\.)+\\w+$",
                "02-1234-5678                         ; ^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
        }, delimiter = ';')
        @DisplayName("throws exception, when actual doesn't match the given regular expression")
        void test1(String actual, @Language("RegExp") String expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .matches(expected))
                    .withMessageMatching(Pattern.quote("It is expected to match the given regular expression, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}expected: '.+'");
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .matches(Pattern.compile(expected)))
                    .withMessageMatching(Pattern.quote("It is expected to match the given pattern, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}expected: '.+'");
        }
    }

}

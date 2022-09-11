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
import io.github.imsejin.common.util.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("CharSequenceAssert")
class CharSequenceAssertTest {

    @Nested
    @DisplayName("method 'isEmpty'")
    class IsEmpty {
        @Test
        @DisplayName("passes, when actual is empty")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer()).isEmpty();
                Asserts.that(new StringBuilder()).isEmpty();
                Asserts.that("").isEmpty();
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {
                " ", "\n", "\t", "\r\n", "alpha",
        })
        @DisplayName("throws exception, when actual is not empty")
        void test1(String source) {
            String message = "It is expected to be empty, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).isEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).isEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).isEmpty())
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEmpty'")
    class IsNotEmpty {
        @ParameterizedTest
        @ValueSource(strings = {
                " ", "\n", "\t", "\r\n", "alpha",
        })
        @DisplayName("passes, when actual is not empty")
        void test0(String source) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).isNotEmpty();
                Asserts.that(new StringBuilder(source)).isNotEmpty();
                Asserts.that(source).isNotEmpty();
            });
        }

        @Test
        @DisplayName("throws exception, when actual is empty")
        void test1() {
            String message = "It is expected not to be empty, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer()).isNotEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder()).isNotEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that("").isNotEmpty())
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSize'")
    class HasSize {
        @ParameterizedTest
        @CsvSource(value = {
                "-                                        | 1",
                "dcc5c541\tb43c\t437b\tbfd3\t675ad3d4ed40 | 36",
                "io.github.imsejin.common.assertion.lang  | 39",
        }, delimiter = '|')
        @DisplayName("passes, when actual has the given length")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSize(expected);
                Asserts.that(new StringBuilder(source)).hasSize(expected);
                Asserts.that(source).hasSize(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "-                                        | 0",
                "dcc5c541\tb43c\t437b\tbfd3\t675ad3d4ed40 | 32",
                "io.github.imsejin.common.assertion.lang  | 30",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't have the given length")
        void test1(String source, int expected) {
            String message = "It is expected to have the given length, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).hasSize(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).hasSize(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).hasSize(expected))
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSize'")
    class DoesNotHaveSize {
        @ParameterizedTest
        @CsvSource(value = {
                "-                                        | 0",
                "dcc5c541\tb43c\t437b\tbfd3\t675ad3d4ed40 | 32",
                "io.github.imsejin.common.assertion.lang  | 30",
        }, delimiter = '|')
        @DisplayName("passes, when actual doesn't have the given length")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).doesNotHaveSize(expected);
                Asserts.that(new StringBuilder(source)).doesNotHaveSize(expected);
                Asserts.that(source).doesNotHaveSize(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "-                                        | 1",
                "dcc5c541\tb43c\t437b\tbfd3\t675ad3d4ed40 | 36",
                "io.github.imsejin.common.assertion.lang  | 39",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual has the given length")
        void test1(String source, int expected) {
            String message = "It is expected not to have the given length, but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).doesNotHaveSize(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).doesNotHaveSize(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).doesNotHaveSize(expected))
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSameSizeAs'")
    class HasSameSizeAs {
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {
                "dcc5c541-b43c-437b-bfd3-675ad3d4ed40",
                "io.github.imsejin.common.assertion.lang",
        })
        @DisplayName("passes, when the actual and the given have the same length")
        void test0(String source) {
            // given
            String expected = StringUtils.repeat(".", source.length());

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSameSizeAs(expected);
                Asserts.that(new StringBuilder(source)).hasSameSizeAs(expected);
                Asserts.that(source).hasSameSizeAs(expected);
            });
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {
                "dcc5c541-b43c-437b-bfd3-675ad3d4ed40",
                "io.github.imsejin.common.assertion.lang",
        })
        @DisplayName("throws exception, when the actual and the given don't have the same length")
        void test1(String source) {
            // given
            String expected = StringUtils.repeat(".", source.length() + 1);

            // expect
            String message = "They are expected to have the same length, but they aren't.";
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).hasSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).hasSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).hasSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).hasSameSizeAs(null))
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveSameSizeAs'")
    class DoesNotHaveSameSizeAs {
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {
                "dcc5c541-b43c-437b-bfd3-675ad3d4ed40",
                "io.github.imsejin.common.assertion.lang",
        })
        @DisplayName("passes, when the actual and the given don't have the same length")
        void test0(String source) {
            // given
            String expected = StringUtils.repeat(".", source.length() + 1);

            // expect
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).doesNotHaveSameSizeAs(expected);
                Asserts.that(new StringBuilder(source)).doesNotHaveSameSizeAs(expected);
                Asserts.that(source).doesNotHaveSameSizeAs(expected);
            });
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {
                "dcc5c541-b43c-437b-bfd3-675ad3d4ed40",
                "io.github.imsejin.common.assertion.lang",
        })
        @DisplayName("throws exception, when the actual and the given have the same length")
        void test1(String source) {
            // given
            String expected = StringUtils.repeat(".", source.length());

            // expect
            String message = "They are expected not to have the same length, but they are.";
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).doesNotHaveSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).doesNotHaveSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).doesNotHaveSameSizeAs(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).doesNotHaveSameSizeAs(null))
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'contains'")
    class Contains {
        @ParameterizedTest
        @CsvSource(value = {
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry | ing",
                "It is a long established fact that a reader will be distracted            | be distracted",
                "Lorem Ipsum is not simply random text.                                    | Lorem Ipsum",
                "There are many variations of passages of Lorem Ipsum available            | are",
                "All the Lorem Ipsum generators tend to repeat predefined chunks           | k",
        }, delimiter = '|')
        @DisplayName("passes, when actual contains the given character(s)")
        void test0(String source, String expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).contains(expected);
                Asserts.that(new StringBuilder(source)).contains(expected);
                Asserts.that(source).contains(expected);
                Asserts.that(source).contains("");
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry | industries",
                "It is a long established fact that a reader will be distracted            | ",
                "Lorem Ipsum is not simply random text.                                    | LoremIpsum",
                "There are many variations of passages of Lorem Ipsum available            | is",
                "All the Lorem Ipsum generators tend to repeat predefined chunks           | CHUNK",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual doesn't contain the given character(s)")
        void test1(String source, String expected) {
            String message = "It is expected to contain the given character(s), but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).contains(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).contains(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).contains(expected))
                    .withMessageStartingWith(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotContain'")
    class DoesNotContain {
        @ParameterizedTest
        @CsvSource(value = {
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry | industries",
                "It is a long established fact that a reader will be distracted            | is distracted",
                "Lorem Ipsum is not simply random text.                                    | LoremIpsum",
                "There are many variations of passages of Lorem Ipsum available            | is",
                "All the Lorem Ipsum generators tend to repeat predefined chunks           | CHUNK",
        }, delimiter = '|')
        @DisplayName("passes, when actual doesn't contain the given character(s)")
        void test0(String source, String expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).doesNotContain(expected);
                Asserts.that(new StringBuilder(source)).doesNotContain(expected);
                Asserts.that(source).doesNotContain(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry | ing",
                "It is a long established fact that a reader will be distracted            | ",
                "Lorem Ipsum is not simply random text.                                    | Lorem Ipsum",
                "There are many variations of passages of Lorem Ipsum available            | are",
                "All the Lorem Ipsum generators tend to repeat predefined chunks           | k",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual contains the given character(s)")
        void test1(String source, String expected) {
            String message = "It is expected not to contain the given character(s), but it is.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuffer(source)).doesNotContain(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(new StringBuilder(source)).doesNotContain(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).doesNotContain(expected))
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(source).doesNotContain(""))
                    .withMessageStartingWith(message);
        }
    }

}

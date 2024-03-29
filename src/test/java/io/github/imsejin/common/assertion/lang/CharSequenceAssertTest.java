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

import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.SizeAssertable;
import io.github.imsejin.common.util.StringUtils;

import static org.assertj.core.api.Assertions.*;

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
                "\u0000", " ", "\t", "\\", "alpha",
        })
        @DisplayName("throws exception, when actual is not empty")
        void test1(String source) {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_IS_EMPTY) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual.size: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .isEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .isEmpty())
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .isEmpty())
                    .withMessageMatching(message);
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

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer())
                            .isNotEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder())
                            .isNotEmpty())
                    .withMessageStartingWith(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that("")
                            .isNotEmpty())
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
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSize(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSize(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSize(expected))
                    .withMessageMatching(message);
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
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .doesNotHaveSize(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .doesNotHaveSize(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .doesNotHaveSize(expected))
                    .withMessageMatching(message);
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
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS) +
                    "\n {4}actual: '.*'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '(.+|null)'" +
                    "\n {4}expected\\.size: '([0-9]+|null)'";
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSameSizeAs(null))
                    .withMessageMatching(message);
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
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS) +
                    "\n {4}actual: '.*'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '(.*|null)'" +
                    "\n {4}expected\\.size: '([0-9]+|null)'";
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .doesNotHaveSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .doesNotHaveSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .doesNotHaveSameSizeAs(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .doesNotHaveSameSizeAs(null))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThan'")
    class HasSizeGreaterThan {
        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 6",
                "JHZYAeWERX     | 8",
                "URtXAkJzXypl   | 10",
                "bCpLvWRuRxDVeh | 12",
        }, delimiter = '|')
        @DisplayName("passes, when actual has size greater than the given size")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSizeGreaterThan(expected);
                Asserts.that(new StringBuilder(source)).hasSizeGreaterThan(expected);
                Asserts.that(source).hasSizeGreaterThan(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 8",
                "JHZYAeWERX     | 10",
                "URtXAkJzXypl   | 14",
                "bCpLvWRuRxDVeh | 16",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual has size less than or same as the given size")
        void test1(String source, int expected) {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSizeGreaterThan(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSizeGreaterThan(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSizeGreaterThan(expected))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeGreaterThanOrEqualTo'")
    class HasSizeGreaterThanOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 8",
                "JHZYAeWERX     | 10",
                "URtXAkJzXypl   | 10",
                "bCpLvWRuRxDVeh | 12",
        }, delimiter = '|')
        @DisplayName("passes, when actual has size greater than or same as the given size")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSizeGreaterThanOrEqualTo(expected);
                Asserts.that(new StringBuilder(source)).hasSizeGreaterThanOrEqualTo(expected);
                Asserts.that(source).hasSizeGreaterThanOrEqualTo(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 10",
                "JHZYAeWERX     | 12",
                "URtXAkJzXypl   | 14",
                "bCpLvWRuRxDVeh | 16",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual has size less than the given size")
        void test1(String source, int expected) {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSizeGreaterThanOrEqualTo(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSizeGreaterThanOrEqualTo(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSizeGreaterThanOrEqualTo(expected))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThan'")
    class HasSizeLessThan {
        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 10",
                "JHZYAeWERX     | 12",
                "URtXAkJzXypl   | 14",
                "bCpLvWRuRxDVeh | 16",
        }, delimiter = '|')
        @DisplayName("passes, when actual has size less than the given size")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSizeLessThan(expected);
                Asserts.that(new StringBuilder(source)).hasSizeLessThan(expected);
                Asserts.that(source).hasSizeLessThan(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 8",
                "JHZYAeWERX     | 10",
                "URtXAkJzXypl   | 10",
                "bCpLvWRuRxDVeh | 12",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual has size greater than or same as the given size")
        void test1(String source, int expected) {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSizeLessThan(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSizeLessThan(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSizeLessThan(expected))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasSizeLessThanOrEqualTo'")
    class HasSizeLessThanOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 8",
                "JHZYAeWERX     | 10",
                "URtXAkJzXypl   | 14",
                "bCpLvWRuRxDVeh | 16",
        }, delimiter = '|')
        @DisplayName("passes, when actual has size less than or same as the given size")
        void test0(String source, int expected) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(new StringBuffer(source)).hasSizeLessThanOrEqualTo(expected);
                Asserts.that(new StringBuilder(source)).hasSizeLessThanOrEqualTo(expected);
                Asserts.that(source).hasSizeLessThanOrEqualTo(expected);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {
                "xYSAyUlK       | 6",
                "JHZYAeWERX     | 8",
                "URtXAkJzXypl   | 10",
                "bCpLvWRuRxDVeh | 12",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual has size greater than the given size")
        void test1(String source, int expected) {
            String message = Pattern.quote(SizeAssertable.DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO) +
                    "\n {4}actual: '.+'" +
                    "\n {4}actual\\.size: '[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .hasSizeLessThanOrEqualTo(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .hasSizeLessThanOrEqualTo(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .hasSizeLessThanOrEqualTo(expected))
                    .withMessageMatching(message);
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
        void test0(CharSequence source, CharSequence expected) {
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
        void test1(CharSequence source, CharSequence expected) {
            String message = Pattern.quote("It is expected to contain the given character(s), but it isn't.") +
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .contains(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .contains(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .contains(expected))
                    .withMessageMatching(message);
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
        void test0(CharSequence source, CharSequence expected) {
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
        void test1(CharSequence source, CharSequence expected) {
            String message = Pattern.quote("It is expected not to contain the given character(s), but it is.") +
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuffer(source))
                            .doesNotContain(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(new StringBuilder(source))
                            .doesNotContain(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .doesNotContain(expected))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(source)
                            .doesNotContain(""))
                    .withMessageMatching(message);
        }
    }

}

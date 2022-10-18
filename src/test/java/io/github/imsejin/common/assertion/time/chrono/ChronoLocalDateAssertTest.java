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

package io.github.imsejin.common.assertion.time.chrono;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.YearAssertable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.common.Switch;
import org.junit.jupiter.params.converter.ConvertJavaTime;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.RandomJavaTimeSource;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ChronoLocalDateAssert")
class ChronoLocalDateAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-23",
                "1918-12-31 | 1918-12-31",
                "2022-05-19 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1918-12-31",
                "2022-05-19 | 1592-05-23",
                "1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected))
                    .withMessageStartingWith("They are expected to be equal, but they aren't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1918-12-31",
                "2022-05-19 | 1592-05-23",
                "1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-23",
                "1918-12-31 | 1918-12-31",
                "2022-05-19 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .withMessageStartingWith("They are expected to be not equal, but they are.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBefore'")
    class IsBefore {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-24",
                "1918-12-31 | 1919-01-01",
                "1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24 | 1592-05-23",
                "1918-12-31 | 1918-12-31",
                "2022-05-19 | 1918-12-31",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBefore(expected))
                    .withMessageStartingWith("It is expected to be before than the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBeforeOrEqualTo'")
    class IsBeforeOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-24",
                "1918-12-31 | 1918-12-31",
                "1919-01-01 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24 | 1592-05-23",
                "1919-01-01 | 1918-12-31",
                "2022-05-19 | 1918-12-31",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be before than or equal to the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAfter'")
    class IsAfter {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24 | 1592-05-23",
                "1919-01-01 | 1918-12-31",
                "2022-05-19 | 1918-12-31",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-24",
                "1918-12-31 | 1918-12-31",
                "1919-01-01 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfter(expected))
                    .withMessageStartingWith("It is expected to be after than the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAfterOrEqualTo'")
    class IsAfterOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24 | 1592-05-23",
                "1918-12-31 | 1918-12-31",
                "2022-05-19 | 1918-12-31",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-24",
                "1918-12-31 | 1919-01-01",
                "1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBetween'")
    class IsBetween {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-23 | 1592-05-31",
                "1919-01-01 | 1918-12-31 | 1919-01-02",
                "2022-05-19 | 1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value inclusively")
        void test0(LocalDate actual, LocalDate start, LocalDate end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-22 | 1592-05-23 | 1592-05-31",
                "1919-02-01 | 1918-12-31 | 1919-01-02",
                "2022-05-20 | 1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value inclusively")
        void test1(LocalDate actual, LocalDate start, LocalDate end) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBetween(start, end))
                    .withMessageStartingWith("It is expected to be");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isStrictlyBetween'")
    class IsStrictlyBetween {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24 | 1592-05-23 | 1592-05-31",
                "1919-01-01 | 1918-12-31 | 1919-01-02",
                "2022-05-18 | 1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value exclusively")
        void test0(LocalDate actual, LocalDate start, LocalDate end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23 | 1592-05-23 | 1592-05-31",
                "1919-02-01 | 1918-12-31 | 1919-01-02",
                "2022-05-19 | 1918-12-31 | 2022-05-19",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value exclusively")
        void test1(LocalDate actual, LocalDate start, LocalDate end) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end))
                    .withMessageStartingWith("It is expected to be");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLeapYear'")
    class IsLeapYear {
        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.ON)
        @DisplayName("passes, when actual is leap year")
        void test0(@ConvertJavaTime LocalDate actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLeapYear());
        }

        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.OFF)
        @DisplayName("throws exception, when actual is not leap year")
        void test1(@ConvertJavaTime LocalDate actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isLeapYear())
                    .withMessageMatching(Pattern.quote(YearAssertable.DEFAULT_DESCRIPTION_IS_LEAP_YEAR) +
                            "\n {4}actual: '[0-9]{4}-[0-9]{2}-[0-9]{2}'");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotLeapYear'")
    class IsNotLeapYear {
        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.OFF)
        @DisplayName("passes, when actual is not leap year")
        void test0(@ConvertJavaTime LocalDate actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotLeapYear());
        }

        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.ON)
        @DisplayName("throws exception, when actual is leap year")
        void test1(@ConvertJavaTime LocalDate actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isNotLeapYear())
                    .withMessageMatching(Pattern.quote(YearAssertable.DEFAULT_DESCRIPTION_IS_NOT_LEAP_YEAR) +
                            "\n {4}actual: '[0-9]{4}-[0-9]{2}-[0-9]{2}'");
        }
    }

}

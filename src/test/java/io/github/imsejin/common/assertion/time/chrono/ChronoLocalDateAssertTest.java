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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.common.Switch;
import org.junit.jupiter.params.converter.ConvertJavaTime;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.RandomDateTimeSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ChronoLocalDateAssert")
class ChronoLocalDateAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-23",
                "1918-12-31,1918-12-31",
                "2022-05-19,2022-05-19",
        })
        @DisplayName("passes, when actual is equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1918-12-31",
                "2022-05-19,1592-05-23",
                "1918-12-31,2022-05-19",
        })
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEqualTo(expected))
                    .withMessageStartingWith("They are expected to be equal, but they aren't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1918-12-31",
                "2022-05-19,1592-05-23",
                "1918-12-31,2022-05-19",
        })
        @DisplayName("passes, when actual is not equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-23",
                "1918-12-31,1918-12-31",
                "2022-05-19,2022-05-19",
        })
        @DisplayName("throws exception, when actual is equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected))
                    .withMessageStartingWith("They are expected to be not equal, but they are.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isBefore'")
    class IsBefore {
        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-24",
                "1918-12-31,1919-01-01",
                "1918-12-31,2022-05-19",
        })
        @DisplayName("passes, when actual is before than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-24,1592-05-23",
                "1918-12-31,1918-12-31",
                "2022-05-19,1918-12-31",
        })
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBefore(expected))
                    .withMessageStartingWith("It is expected to be before than the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isBeforeOrEqualTo'")
    class IsBeforeOrEqualTo {
        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-24",
                "1918-12-31,1918-12-31",
                "1919-01-01,2022-05-19",
        })
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-24,1592-05-23",
                "1919-01-01,1918-12-31",
                "2022-05-19,1918-12-31",
        })
        @DisplayName("throws exception, when actual is after than other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be before than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAfter'")
    class IsAfter {
        @ParameterizedTest
        @CsvSource({
                "1592-05-24,1592-05-23",
                "1919-01-01,1918-12-31",
                "2022-05-19,1918-12-31",
        })
        @DisplayName("passes, when actual is after than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-24",
                "1918-12-31,1918-12-31",
                "1919-01-01,2022-05-19",
        })
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfter(expected))
                    .withMessageStartingWith("It is expected to be after than the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAfterOrEqualTo'")
    class IsAfterOrEqualTo {
        @ParameterizedTest
        @CsvSource({
                "1592-05-24,1592-05-23",
                "1918-12-31,1918-12-31",
                "2022-05-19,1918-12-31",
        })
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23,1592-05-24",
                "1918-12-31,1919-01-01",
                "1918-12-31,2022-05-19",
        })
        @DisplayName("throws exception, when actual is before than other")
        void test1(LocalDate actual, LocalDate expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLeapYear'")
    class IsLeapYear {
        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.ON)
        @DisplayName("passes, when actual is leap year")
        void test0(@ConvertJavaTime LocalDate actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLeapYear());
        }

        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.OFF)
        @DisplayName("throws exception, when actual is not leap year")
        void test1(@ConvertJavaTime LocalDate actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isLeapYear())
                    .withMessageStartingWith("It is expected to be leap year, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotLeapYear'")
    class IsNotLeapYear {
        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.OFF)
        @DisplayName("passes, when actual is not leap year")
        void test0(@ConvertJavaTime LocalDate actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotLeapYear());
        }

        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.ON)
        @DisplayName("throws exception, when actual is leap year")
        void test1(@ConvertJavaTime LocalDate actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isNotLeapYear())
                    .withMessageStartingWith("It is expected not to be leap year, but it is.");
        }
    }

}

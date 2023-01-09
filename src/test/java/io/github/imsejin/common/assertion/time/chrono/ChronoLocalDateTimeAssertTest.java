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

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.github.imsejin.common.assertion.Asserts;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ChronoLocalDateTimeAssert")
class ChronoLocalDateTimeAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000000 | 2022-05-19T23:59:59.999999999",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012346",
                "2022-05-19T23:59:59.999999999 | 1592-05-23T00:00:00.000000000",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000000 | 2022-05-19T23:59:59.999999999",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012346",
                "2022-05-19T23:59:59.999999999 | 1592-05-23T00:00:00.000000000",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000001",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:57.789012345",
                "2022-05-19T23:59:59.999999999 | 3022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000001 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "3022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000001",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 3022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000001 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:57.789012345 | 1918-12-31T12:34:56.789012345",
                "3022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000001 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:57.789012345 | 1918-12-31T12:34:56.789012345",
                "3022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000001",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 3022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000001 | 1592-05-23T00:00:00.000000000",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:56.789012345",
                "3022-05-19T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000001",
                "1918-12-31T12:34:56.789012345 | 1918-12-31T12:34:57.789012345",
                "2022-05-19T23:59:59.999999999 | 3022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
                "1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000001",
                "1919-01-01T12:34:56.789012345 | 1918-12-31T12:34:56.789012345 | 1919-01-02T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 1918-12-31T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value inclusively")
        void test0(LocalDateTime actual, LocalDateTime start, LocalDateTime end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-22T00:00:00.000000000 | 1592-05-23T00:00:00.000000000 | 1592-05-31T00:00:00.000000000",
                "1919-02-01T12:34:56.789012345 | 1918-12-31T12:34:56.789012345 | 1919-01-02T12:34:56.789012345",
                "2022-05-20T23:59:59.999999999 | 1918-12-31T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value inclusively")
        void test1(LocalDateTime actual, LocalDateTime start, LocalDateTime end) {
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
                "1592-05-23T00:00:00.000000001 | 1592-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000002",
                "1919-01-01T12:34:56.789012345 | 1918-12-31T12:34:56.789012345 | 1919-01-02T12:34:56.789012345",
                "2022-05-19T23:59:59.999999998 | 1918-12-31T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value exclusively")
        void test0(LocalDateTime actual, LocalDateTime start, LocalDateTime end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1591-05-23T00:00:00.000000000 | 1592-05-23T00:00:00.000000000 | 1592-05-31T00:00:00.000000000",
                "1919-02-01T12:34:56.789012345 | 1918-12-31T12:34:56.789012345 | 1919-01-02T12:34:56.789012345",
                "2022-05-19T23:59:59.999999999 | 1918-12-31T23:59:59.999999999 | 2022-05-19T23:59:59.999999999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value exclusively")
        void test1(LocalDateTime actual, LocalDateTime start, LocalDateTime end) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end))
                    .withMessageStartingWith("It is expected to be");
        }
    }

}

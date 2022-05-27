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

package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("DurationAssert")
class DurationAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT6H",
                "PT30M,          PT30M",
                "PT59S,          PT59S",
                "PT0.000000007S, PT0.000000007S",
                "PT6H30M59S,     PT6H30M59S",
                "PT-1M-42S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.075S",
        })
        @DisplayName("passes, when actual is equal to other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT29M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H30M59S,     PT6H31M",
                "PT-2M-42S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.75S",
        })
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT29M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H30M59S,     PT6H31M",
                "PT-2M-42S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.75S",
        })
        @DisplayName("passes, when actual is not equal to other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT6H",
                "PT30M,          PT30M",
                "PT59S,          PT59S",
                "PT0.000000007S, PT0.000000007S",
                "PT6H30M59S,     PT6H30M59S",
                "PT-1M-42S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.075S",
        })
        @DisplayName("throws exception, when actual is equal to other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT5H",
                "PT30M,          PT29M",
                "PT59S,          PT58S",
                "PT0.000000008S, PT0.000000007S",
                "PT6H30M59S,     PT6H29M59S",
                "PT-1M-42S,      PT-1M-43S",
                "PT5M13.075S,    PT5M13.0075S",
        })
        @DisplayName("passes, when actual is greater than other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT31M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H30M59S,     PT6H30M59S",
                "PT-2M-43S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.075S",
        })
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT5H",
                "PT30M,          PT29M",
                "PT59S,          PT58S",
                "PT0.000000008S, PT0.000000007S",
                "PT6H30M59S,     PT6H30M59S",
                "PT-1M-42S,      PT-1M-43S",
                "PT5M13.075S,    PT5M13.075S",
        })
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT31M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H29M59S,     PT6H30M59S",
                "PT-2M-43S,      PT-1M-42S",
                "PT5M13.0075S,    PT5M13.075S",
        })
        @DisplayName("throws exception, when actual is less than other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT31M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H29M59S,     PT6H30M59S",
                "PT-2M-43S,      PT-1M-42S",
                "PT5M13.0075S,    PT5M13.075S",
        })
        @DisplayName("passes, when actual is less than other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT5H",
                "PT30M,          PT29M",
                "PT59S,          PT58S",
                "PT0.000000008S, PT0.000000007S",
                "PT6H30M59S,     PT6H29M59S",
                "PT-1M-42S,      PT-1M-43S",
                "PT5M13.075S,    PT5M13.0075S",
        })
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT7H",
                "PT30M,          PT31M",
                "PT58S,          PT59S",
                "PT0.000000007S, PT0.000000008S",
                "PT6H30M59S,     PT6H30M59S",
                "PT-2M-43S,      PT-1M-42S",
                "PT5M13.075S,    PT5M13.075S",
        })
        @DisplayName("passes, when actual is less than or equal to other")
        void test0(Duration actual, Duration expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "PT6H,           PT5H",
                "PT30M,          PT29M",
                "PT59S,          PT58S",
                "PT0.000000008S, PT0.000000007S",
                "PT6H30M59S,     PT6H29M59S",
                "PT-1M-42S,      PT-1M-43S",
                "PT5M13.075S,    PT5M13.0075S",
        })
        @DisplayName("throws exception, when actual is greater than other")
        void test1(Duration actual, Duration expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "PT6H", "PT30M", "PT59S", "PT0.000000008S", "PT6H30M", "PT1M42S", "PT5M13.075S",
        })
        @DisplayName("passes, when actual is positive")
        void test0(Duration actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PT-6H", "PT-30M", "PT0S", "PT-1.000000008S", "PT-6H-30M", "PT-1M-42S", "PT-5M-13.075S",
        })
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(Duration actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isPositive());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "PT6H", "PT30M", "PT0S", "PT0.000000008S", "PT6H30M", "PT1M42S", "PT5M13.075S",
        })
        @DisplayName("passes, when actual is zero or positive")
        void test0(Duration actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PT-6H", "PT-30M", "PT-59S", "PT-1.000000008S", "PT-6H-30M", "PT-1M-42S", "PT-5M-13.075S",
        })
        @DisplayName("throws exception, when actual is negative")
        void test1(Duration actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isZeroOrPositive());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "PT-6H", "PT-30M", "PT-59S", "PT-1.000000008S", "PT-6H-30M", "PT-1M-42S", "PT-5M-13.075S",
        })
        @DisplayName("passes, when actual is negative")
        void test0(Duration actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PT6H", "PT30M", "PT0S", "PT0.000000008S", "PT6H30M", "PT1M42S", "PT5M13.075S",
        })
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(Duration actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isNegative());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "PT-6H", "PT-30M", "PT0S", "PT-1.000000008S", "PT-6H-30M", "PT-1M-42S", "PT-5M-13.075S",
        })
        @DisplayName("passes, when actual is zero or negative")
        void test0(Duration actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PT6H", "PT30M", "PT59S", "PT0.000000008S", "PT6H30M", "PT1M42S", "PT5M13.075S",
        })
        @DisplayName("throws exception, when actual is positive")
        void test1(Duration actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isZeroOrNegative());
        }
    }

}

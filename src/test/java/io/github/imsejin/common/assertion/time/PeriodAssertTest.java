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

import java.time.Period;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PeriodAssert")
class PeriodAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P2Y",
                "P6M      | P6M",
                "P30D     | P30D",
                "P2Y6M30D | P2Y6M30D",
                "P-1M-42D | P-1M-42D",
                "P5Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y5M30D",
                "P-1M-42D | P1M42D",
                "P5Y13M   | P-5Y-13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y5M30D",
                "P-1M-42D | P1M42D",
                "P5Y13M   | P-5Y-13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P2Y",
                "P6M      | P6M",
                "P30D     | P30D",
                "P2Y6M30D | P2Y6M30D",
                "P-1M-42D | P-1M-42D",
                "P5Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThan'")
    class IsGreaterThan {
        @ParameterizedTest
        @CsvSource(value = {
                "P3Y      | P2Y",
                "P7M      | P6M",
                "P31D     | P30D",
                "P2Y6M31D | P2Y6M30D",
                "P1M42D   | P-1M-42D",
                "P5Y13M   | P4Y13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is greater than other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y6M31D",
                "P-1M-42D | P-1M-42D",
                "P4Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is less than or equal to other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isGreaterThan(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isGreaterThanOrEqualTo'")
    class IsGreaterThanOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "P3Y      | P2Y",
                "P7M      | P6M",
                "P31D     | P30D",
                "P2Y6M31D | P2Y6M30D",
                "P-1M-42D | P-1M-42D",
                "P5Y13M   | P4Y13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is greater than or equal to other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y6M31D",
                "P-1M-42D | P-1M-41D",
                "P4Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is less than other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(
                    () -> Asserts.that(actual).isGreaterThanOrEqualTo(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThan'")
    class IsLessThan {
        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y6M31D",
                "P-1M-42D | P-1M-41D",
                "P4Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is less than other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P3Y      | P2Y",
                "P7M      | P6M",
                "P31D     | P30D",
                "P2Y6M31D | P2Y6M30D",
                "P-1M-42D | P-1M-42D",
                "P5Y13M   | P4Y13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is greater than or equal to other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isLessThan(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLessThanOrEqualTo'")
    class IsLessThanOrEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "P2Y      | P3Y",
                "P6M      | P7M",
                "P30D     | P31D",
                "P2Y6M30D | P2Y6M31D",
                "P-1M-42D | P-1M-42D",
                "P4Y13M   | P5Y13M",
        }, delimiter = '|')
        @DisplayName("passes, when actual is less than or equal to other")
        void test0(Period actual, Period expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "P3Y      | P2Y",
                "P7M      | P6M",
                "P31D     | P30D",
                "P2Y6M31D | P2Y6M30D",
                "P1M42D   | P-1M-42D",
                "P5Y13M   | P4Y13M",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is greater than other")
        void test1(Period actual, Period expected) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isLessThanOrEqualTo(expected));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPositive'")
    class IsPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "P2Y", "P6M", "P30D", "P2Y6M30D", "P1M42D", "P5Y13M",
        })
        @DisplayName("passes, when actual is positive")
        void test0(Period actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "P-2Y", "P-6M", "P0D", "P-2Y-6M-30D", "P-1M-42D", "P-5Y-13M",
        })
        @DisplayName("throws exception, when actual is zero or negative")
        void test1(Period actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isPositive());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isZeroOrPositive'")
    class IsZeroOrPositive {
        @ParameterizedTest
        @ValueSource(strings = {
                "P2Y", "P6M", "P0D", "P2Y6M30D", "P1M42D", "P5Y13M",
        })
        @DisplayName("passes, when actual is zero or positive")
        void test0(Period actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrPositive());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "P-2Y", "P-6M", "P-30D", "P-2Y-6M-30D", "P-1M-42D", "P-5Y-13M",
        })
        @DisplayName("throws exception, when actual is negative")
        void test1(Period actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isZeroOrPositive());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNegative'")
    class IsNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "P-2Y", "P-6M", "P-30D", "P-2Y-6M-30D", "P-1M-42D", "P-5Y-13M",
        })
        @DisplayName("passes, when actual is negative")
        void test0(Period actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "P2Y", "P6M", "P0D", "P2Y6M30D", "P1M42D", "P5Y13M",
        })
        @DisplayName("throws exception, when actual is zero or positive")
        void test1(Period actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isNegative());
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isZeroOrNegative'")
    class IsZeroOrNegative {
        @ParameterizedTest
        @ValueSource(strings = {
                "P-2Y", "P-6M", "P0D", "P-2Y-6M-30D", "P-1M-42D", "P-5Y-13M",
        })
        @DisplayName("passes, when actual is zero or negative")
        void test0(Period actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isZeroOrNegative());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "P2Y", "P6M", "P30D", "P2Y6M30D", "P1M42D", "P5Y13M",
        })
        @DisplayName("throws exception, when actual is positive")
        void test1(Period actual) {
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual).isZeroOrNegative());
        }
    }

}

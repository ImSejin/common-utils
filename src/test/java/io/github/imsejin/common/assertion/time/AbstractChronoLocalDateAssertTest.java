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

package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractChronoLocalDateAssert")
class AbstractChronoLocalDateAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isEqualTo")
        @DisplayName("passes, when actual is equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isNotEqualTo")
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(LocalDate actual, LocalDate expected) {
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
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isNotEqualTo")
        @DisplayName("passes, when actual is not equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isEqualTo")
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
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isBefore")
        @DisplayName("passes, when actual is before than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isAfterOrEqualTo")
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
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isBeforeOrEqualTo")
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isAfter")
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
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isAfter")
        @DisplayName("passes, when actual is after than other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isBeforeOrEqualTo")
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
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isAfterOrEqualTo")
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(LocalDate actual, LocalDate expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource("io.github.imsejin.common.assertion.time.AbstractChronoLocalDateAssertTest#isBefore")
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
        @Test
        @DisplayName("passes, when actual is leap year")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not leap year")
        void test1() {

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotLeapYear'")
    class IsNotLeapYear {
        @Test
        @DisplayName("passes, when actual is not leap year")
        void test0() {

        }

        @Test
        @DisplayName("throws exception, when actual is leap year")
        void test1() {

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> isEqualTo() {
        return Stream.of(
                Arguments.of(LocalDate.now(),
                        LocalDate.now().plusWeeks(1).minusDays(7)),
                Arguments.of(LocalDate.now(),
                        LocalDate.from(YearMonth.now().adjustInto(LocalDate.now()))),
                Arguments.of(LocalDate.of(1918, 12, 31),
                        LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()))
        );
    }

    private static Stream<Arguments> isNotEqualTo() {
        return Stream.of(
                Arguments.of(LocalDate.now(),
                        LocalDate.now().minusDays(1)),
                Arguments.of(LocalDate.now(),
                        LocalDate.from(YearMonth.now().minusMonths(1).adjustInto(LocalDate.now()))),
                Arguments.of(LocalDate.of(1918, 12, 31),
                        LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atDay(28)))
        );
    }

    private static Stream<Arguments> isBefore() {
        return Stream.of(
                Arguments.of(LocalDate.now(),
                        LocalDate.now().plusDays(1)),
                Arguments.of(LocalDate.now().plusDays(7),
                        LocalDate.now().plusWeeks(2).minusDays(6)),
                Arguments.of(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atDay(28)),
                        LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()))
        );
    }

    private static Stream<Arguments> isBeforeOrEqualTo() {
        return Stream.concat(isBefore(), isEqualTo());
    }

    private static Stream<Arguments> isAfter() {
        return Stream.of(
                Arguments.of(LocalDate.now().plusDays(1),
                        LocalDate.now()),
                Arguments.of(LocalDate.now().plusWeeks(2).minusDays(6),
                        LocalDate.now().plusDays(7)),
                Arguments.of(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()),
                        LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atDay(28)))
        );
    }

    private static Stream<Arguments> isAfterOrEqualTo() {
        return Stream.concat(isAfter(), isEqualTo());
    }

}

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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ChronoLocalDateTimeAssert")
class ChronoLocalDateTimeAssertTest {

    private static final String FQCN = "io.github.imsejin.common.assertion.time.chrono.ChronoLocalDateTimeAssertTest";
    private static final String IS_EQUAL_TO = FQCN + "#isEqualTo";
    private static final String IS_NOT_EQUAL_TO = FQCN + "#isNotEqualTo";
    private static final String IS_BEFORE = FQCN + "#isBefore";
    private static final String IS_BEFORE_OR_EQUAL_TO = FQCN + "#isBeforeOrEqualTo";
    private static final String IS_AFTER = FQCN + "#isAfter";
    private static final String IS_AFTER_OR_EQUAL_TO = FQCN + "#isAfterOrEqualTo";

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("passes, when actual is equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_NOT_EQUAL_TO)
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
        @MethodSource(IS_NOT_EQUAL_TO)
        @DisplayName("passes, when actual is not equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_EQUAL_TO)
        @DisplayName("throws exception, when actual is equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
        @MethodSource(IS_BEFORE)
        @DisplayName("passes, when actual is before than other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_AFTER_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
        @MethodSource(IS_BEFORE_OR_EQUAL_TO)
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_AFTER)
        @DisplayName("throws exception, when actual is after than other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
        @MethodSource(IS_AFTER)
        @DisplayName("passes, when actual is after than other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_BEFORE_OR_EQUAL_TO)
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
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
        @MethodSource(IS_AFTER_OR_EQUAL_TO)
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(LocalDateTime actual, LocalDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @MethodSource(IS_BEFORE)
        @DisplayName("throws exception, when actual is before than other")
        void test1(LocalDateTime actual, LocalDateTime expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Stream<Arguments> isEqualTo() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))),
                Arguments.of(LocalDateTime.now().withNano(0).plusHours(25).minusMinutes(60),
                        LocalDateTime.now().withNano(0).plusDays(1)),
                Arguments.of(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX),
                        LocalDateTime.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth().atTime(LocalTime.MAX)))
        );
    }

    private static Stream<Arguments> isNotEqualTo() {
        return Stream.of(
                Arguments.of(LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1)),
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX)),
                Arguments.of(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT),
                        LocalDateTime.from(LocalDate.of(2000, 6, 25).atTime(LocalTime.MIDNIGHT)))
        );
    }

    private static Stream<Arguments> isBefore() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0, 1))),
                Arguments.of(LocalDateTime.now().withNano(0).plusHours(25).minusMinutes(61),
                        LocalDateTime.now().withNano(0).plusDays(1)),
                Arguments.of(LocalDateTime.from(YearMonth.of(1918, Month.DECEMBER).atDay(28).atTime(LocalTime.MAX)),
                        LocalDateTime.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth().atTime(LocalTime.MAX)))
        );
    }

    private static Stream<Arguments> isBeforeOrEqualTo() {
        return Stream.concat(isBefore(), isEqualTo());
    }

    private static Stream<Arguments> isAfter() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusMinutes(1),
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))),
                Arguments.of(LocalDateTime.now().withNano(0).plusHours(26).minusMinutes(60),
                        LocalDateTime.now().withNano(0).plusDays(1)),
                Arguments.of(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX),
                        LocalDateTime.from(YearMonth.of(1918, Month.DECEMBER).atDay(1).atTime(LocalTime.MAX)))
        );
    }

    private static Stream<Arguments> isAfterOrEqualTo() {
        return Stream.concat(isAfter(), isEqualTo());
    }

}

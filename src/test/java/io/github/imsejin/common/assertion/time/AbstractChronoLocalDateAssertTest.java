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

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractChronoLocalDateAssert")
class AbstractChronoLocalDateAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now().plusWeeks(1))
                        .isEqualTo(LocalDate.now().plusDays(7));
                Asserts.that(LocalDate.now())
                        .isEqualTo(LocalDate.now());
                Asserts.that(LocalDate.of(1918, 12, 31))
                        .isEqualTo(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            String description = "They are expected to be equal, but they aren't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isEqualTo(LocalDate.now().plusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isEqualTo(LocalDate.now().plusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.of(1950, 6, 25))
                            .isEqualTo(LocalDate.of(2000, 6, 25)))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @Test
        @DisplayName("passes, when actual is not equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now())
                        .isNotEqualTo(LocalDate.now().plusDays(1));
                Asserts.that(LocalDate.now().plusWeeks(1))
                        .isNotEqualTo(LocalDate.now());
                Asserts.that(LocalDate.of(1950, 6, 25))
                        .isNotEqualTo(LocalDate.from(LocalDate.of(2000, 6, 25)));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            String description = "They are expected to be not equal, but they are.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().minusDays(2).plusDays(1))
                            .isNotEqualTo(LocalDate.now().minusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().plusDays(-1))
                            .isNotEqualTo(LocalDate.now().minusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.of(1918, 12, 31))
                            .isNotEqualTo(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth())))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'isBefore'")
    class IsBefore {
        @Test
        @DisplayName("passes, when actual is before than other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now())
                        .isBefore(LocalDate.now().plusDays(1));
                Asserts.that(LocalDate.now().minusWeeks(1))
                        .isBefore(LocalDate.now());
                Asserts.that(LocalDate.of(1950, 6, 25))
                        .isBefore(LocalDate.of(2000, 6, 25));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1() {
            String description = "It is expected to be before than the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().plusDays(1))
                            .isBefore(LocalDate.now()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().plusDays(15))
                            .isBefore(LocalDate.now().plusWeeks(2).minusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()))
                            .isBefore(LocalDate.of(1918, 12, 30)))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'isBeforeOrEqualTo'")
    class IsBeforeOrEqualTo {
        @Test
        @DisplayName("passes, when actual is before than or equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now())
                        .isBeforeOrEqualTo(LocalDate.now().plusDays(1));
                Asserts.that(LocalDate.now().minusDays(1))
                        .isBeforeOrEqualTo(LocalDate.now().plusDays(1));
                Asserts.that(LocalDate.of(1950, 6, 25))
                        .isBeforeOrEqualTo(LocalDate.of(2000, 6, 25));
                Asserts.that(LocalDate.now().plusMonths(1))
                        .isBeforeOrEqualTo(LocalDate.now().plusDays(31));
                Asserts.that(LocalDate.now().plusYears(1))
                        .isBeforeOrEqualTo(LocalDate.now().plusYears(1).plusDays(1));
                Asserts.that(LocalDate.of(1918, 12, 31))
                        .isBeforeOrEqualTo(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is after than other")
        void test1() {
            String description = "It is expected to be before than or equal to the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isBeforeOrEqualTo(LocalDate.now().minusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().minusDays(-1))
                            .isBeforeOrEqualTo(LocalDate.now()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()))
                            .isBeforeOrEqualTo(LocalDate.of(1918, 12, 30)))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'isAfter'")
    class IsAfter {
        @Test
        @DisplayName("passes, when actual is after than other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now().plusDays(1))
                        .isAfter(LocalDate.now());
                Asserts.that(LocalDate.now().minusMonths(1))
                        .isAfter(LocalDate.now().minusDays(32));
                Asserts.that(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()))
                        .isAfter(LocalDate.of(1918, 12, 30));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1() {
            String description = "It is expected to be after than the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isAfter(LocalDate.now()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isAfter(LocalDate.now().plusMonths(2)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.of(1950, 6, 25))
                            .isAfter(LocalDate.from(LocalDate.of(2000, 6, 25))))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().minusWeeks(2))
                            .isAfter(LocalDate.now().minusDays(14)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().minusMonths(1).withDayOfMonth(1))
                            .isAfter(LocalDate.from(YearMonth.now().minusMonths(1).atDay(5))))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.from(LocalDate.of(1918, 12, 31)))
                            .isAfter(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth())))
                    .withMessageStartingWith(description);
        }
    }

    @Nested
    @DisplayName("method 'isAfterOrEqualTo'")
    class IsAfterOrEqualTo {
        @Test
        @DisplayName("passes, when actual is after than or equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDate.now().plusDays(25))
                        .isAfterOrEqualTo(LocalDate.now().plusDays(24));
                Asserts.that(LocalDate.now())
                        .isAfterOrEqualTo(LocalDate.now());
                Asserts.that(LocalDate.of(1918, 12, 31))
                        .isAfterOrEqualTo(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()));
                Asserts.that(LocalDate.now().minusWeeks(1))
                        .isAfterOrEqualTo(LocalDate.now().minusDays(8));
                Asserts.that(LocalDate.now().plusYears(1))
                        .isAfterOrEqualTo(LocalDate.now().plusDays(365));
                Asserts.that(LocalDate.from(LocalDate.of(1918, 12, 31)))
                        .isAfterOrEqualTo(LocalDate.from(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth()));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is before than other")
        void test1() {
            String description = "It is expected to be after than or equal to the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now())
                            .isAfterOrEqualTo(LocalDate.now().plusDays(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.now().minusDays(2))
                            .isAfterOrEqualTo(LocalDate.now()))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDate.of(1950, 6, 25))
                            .isAfterOrEqualTo(LocalDate.of(2000, 6, 25)))
                    .withMessageStartingWith(description);
        }
    }

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

}

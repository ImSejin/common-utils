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

import java.time.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AbstractChronoLocalDateTimeAssert")
class AbstractChronoLocalDateTimeAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @Test
        @DisplayName("passes, when actual is equal to other")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(LocalDateTime.now().plusHours(25).minusMinutes(60).withNano(0))
                        .isEqualTo(LocalDateTime.now().plusDays(1).withNano(0));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
                Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                        .isEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MAX));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not equal to other")
        void test1() {
            String description = "They are expected to be equal, but they aren't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now())
                            .isEqualTo(LocalDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                            .isEqualTo(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT)))
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
                Asserts.that(LocalDateTime.now())
                        .isNotEqualTo(LocalDateTime.now().plusSeconds(1));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isNotEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
                Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                        .isNotEqualTo(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is equal to other")
        void test1() {
            String description = "They are expected to be not equal, but they are.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now().plusHours(25).minusMinutes(60).withNano(0))
                            .isNotEqualTo(LocalDateTime.now().plusDays(1).withNano(0)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isNotEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                            .isNotEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MAX)))
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
                Asserts.that(LocalDateTime.now())
                        .isBefore(LocalDateTime.now().plusSeconds(1));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
                Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                        .isBefore(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1() {
            String description = "It is expected to be before than the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now().plusHours(25).withNano(0))
                            .isBefore(LocalDateTime.now().plusDays(1).withNano(0)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                            .isBefore(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MIDNIGHT)))
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
                Asserts.that(LocalDateTime.now())
                        .isBeforeOrEqualTo(LocalDateTime.now().plusSeconds(1));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isBeforeOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
                Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                        .isBeforeOrEqualTo(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT));
                Asserts.that(LocalDateTime.now().plusHours(25).minusMinutes(60).withNano(0))
                        .isBeforeOrEqualTo(LocalDateTime.now().plusDays(1).withNano(0));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isBeforeOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
                Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                        .isBeforeOrEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MAX));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is after than other")
        void test1() {
            String description = "It is expected to be before than or equal to the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now().plusHours(25).withNano(0))
                            .isBeforeOrEqualTo(LocalDateTime.now().plusDays(1).withNano(0)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                            .isBeforeOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                            .isBeforeOrEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MIDNIGHT)))
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
                Asserts.that(LocalDateTime.now().plusHours(25).withNano(0))
                        .isAfter(LocalDateTime.now().plusDays(1).withNano(0));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                        .isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
                Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                        .isAfter(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MIDNIGHT));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1() {
            String description = "It is expected to be after than the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now())
                            .isAfter(LocalDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                            .isAfter(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now().plusHours(25).minusMinutes(60).withNano(0))
                            .isAfter(LocalDateTime.now().plusDays(1).withNano(0)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                            .isAfter(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MAX)))
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
                Asserts.that(LocalDateTime.now().plusHours(25).withNano(0))
                        .isAfterOrEqualTo(LocalDateTime.now().plusDays(1).withNano(0));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                        .isAfterOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
                Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                        .isAfterOrEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MIDNIGHT));
                Asserts.that(LocalDateTime.now().plusHours(25).minusMinutes(60).withNano(0))
                        .isAfterOrEqualTo(LocalDateTime.now().plusDays(1).withNano(0));
                Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                        .isAfterOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
                Asserts.that(LocalDateTime.of(LocalDate.of(1918, 12, 31), LocalTime.MAX))
                        .isAfterOrEqualTo(LocalDateTime.of(YearMonth.of(1918, Month.DECEMBER).atEndOfMonth(), LocalTime.MAX));
            });
        }

        @Test
        @DisplayName("throws exception, when actual is before than other")
        void test1() {
            String description = "It is expected to be after than or equal to the other, but it isn't.";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.now())
                            .isAfterOrEqualTo(LocalDateTime.now().plusSeconds(1)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
                            .isAfterOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)))
                    .withMessageStartingWith(description);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(LocalDateTime.of(LocalDate.of(1950, 6, 25), LocalTime.MIDNIGHT))
                            .isAfterOrEqualTo(LocalDateTime.of(LocalDate.of(2000, 6, 25), LocalTime.MIDNIGHT)))
                    .withMessageStartingWith(description);
        }
    }

}

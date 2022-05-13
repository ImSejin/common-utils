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
import io.github.imsejin.common.util.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("YearAssert")
class YearAssertTest {

    @Nested
    @DisplayName("method 'isLeapYear'")
    class IsLeapYear {
        @Test
        @DisplayName("passes, when actual is leap year")
        void test0() {
            // given
            List<Year> leapYears = IntStream.range(0, 10000)
                    .filter(DateTimeUtils::isLeapYear).mapToObj(Year::of).collect(toList());

            // expect
            leapYears.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isLeapYear()));
        }

        @Test
        @DisplayName("throws exception, when actual is not leap year")
        void test1() {
            // given
            List<Year> nonLeapYears = IntStream.range(0, 10000)
                    .filter(n -> !DateTimeUtils.isLeapYear(n)).mapToObj(Year::of).collect(toList());

            // expect
            nonLeapYears.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLeapYear())
                    .withMessageStartingWith("It is expected to be leap year, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotLeapYear'")
    class IsNotLeapYear {
        @Test
        @DisplayName("passes, when actual is not leap year")
        void test0() {
            // given
            List<Year> nonLeapYears = IntStream.range(0, 10000)
                    .filter(n -> !DateTimeUtils.isLeapYear(n)).mapToObj(Year::of).collect(toList());

            // expect
            nonLeapYears.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNotLeapYear()));
        }

        @Test
        @DisplayName("throws exception, when actual is leap year")
        void test1() {
            // given
            List<Year> leapYears = IntStream.range(0, 10000)
                    .filter(DateTimeUtils::isLeapYear).mapToObj(Year::of).collect(toList());

            // expect
            leapYears.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotLeapYear())
                    .withMessageStartingWith("It is expected not to be leap year, but it is."));
        }
    }

}

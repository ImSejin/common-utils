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
import org.junit.jupiter.params.common.Switch;
import org.junit.jupiter.params.converter.ConvertJavaTime;
import org.junit.jupiter.params.provider.RandomDateTimeSource;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("YearAssert")
class YearAssertTest {

    @Nested
    @DisplayName("method 'isLeapYear'")
    class IsLeapYear {
        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.ON)
        @DisplayName("passes, when actual is leap year")
        void test0(@ConvertJavaTime Year actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isLeapYear());
        }

        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.OFF)
        @DisplayName("throws exception, when actual is not leap year")
        void test1(@ConvertJavaTime Year actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLeapYear())
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
        void test0(@ConvertJavaTime Year actual) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotLeapYear());
        }

        @ParameterizedTest
        @RandomDateTimeSource(leapYear = Switch.ON)
        @DisplayName("throws exception, when actual is leap year")
        void test1(@ConvertJavaTime Year actual) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotLeapYear())
                    .withMessageStartingWith("It is expected not to be leap year, but it is.");
        }
    }

}

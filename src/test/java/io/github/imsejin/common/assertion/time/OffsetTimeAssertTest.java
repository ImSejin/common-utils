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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("OffsetTimeAssert")
class OffsetTimeAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-18:00",
                "05:15:43-15:30 | 05:15:43-15:30",
                "02:25:52-12:00 | 02:25:52-12:00",
                "19:39:07-10:00 | 19:39:07-10:00",
                "04:04:16-07:00 | 04:04:16-07:00",
                "06:36:50-00:00 | 06:36:50-00:00",
                "04:52:25+00:00 | 04:52:25+00:00",
                "04:00:04+01:00 | 04:00:04+01:00",
                "17:22:16+03:00 | 17:22:16+03:00",
                "08:30:21+09:00 | 08:30:21+09:00",
                "11:19:43+10:00 | 11:19:43+10:00",
                "23:59:59+18:00 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:01Z",
                "12:34:56-18:00 | 12:34:56+18:00",
                "05:15:43-15:30 | 05:15:43-15:00",
                "02:25:52-11:00 | 02:25:52-12:00",
                "19:39:07-10:00 | 19:39:08-10:00",
                "04:04:17-07:00 | 04:04:16-07:00",
                "06:36:50-00:00 | 07:36:50-00:00",
                "04:52:35+00:00 | 04:52:25+00:00",
                "04:00:04+01:00 | 04:04:04+01:00",
                "17:22:16+03:30 | 17:22:16+03:00",
                "08:30:21+09:00 | 08:30:21+08:00",
                "11:19:43+10:00 | 11:19:43+11:00",
                "23:59:59-18:00 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:00Z      | 00:00:01Z",
                "12:34:56-18:00 | 12:34:56+18:00",
                "05:15:43-15:30 | 05:15:43-15:00",
                "02:25:52-11:00 | 02:25:52-12:00",
                "19:39:07-10:00 | 19:39:08-10:00",
                "04:04:17-07:00 | 04:04:16-07:00",
                "06:36:50-00:00 | 07:36:50-00:00",
                "04:52:35+00:00 | 04:52:25+00:00",
                "04:00:04+01:00 | 04:04:04+01:00",
                "17:22:16+03:30 | 17:22:16+03:00",
                "08:30:21+09:00 | 08:30:21+08:00",
                "11:19:43+10:00 | 11:19:43+11:00",
                "23:59:59-18:00 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-18:00",
                "05:15:43-15:30 | 05:15:43-15:30",
                "02:25:52-12:00 | 02:25:52-12:00",
                "19:39:07-10:00 | 19:39:07-10:00",
                "04:04:16-07:00 | 04:04:16-07:00",
                "06:36:50-00:00 | 06:36:50-00:00",
                "04:52:25+00:00 | 04:52:25+00:00",
                "04:00:04+01:00 | 04:00:04+01:00",
                "17:22:16+03:00 | 17:22:16+03:00",
                "08:30:21+09:00 | 08:30:21+09:00",
                "11:19:43+10:00 | 11:19:43+10:00",
                "23:59:59+18:00 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:00Z      | 00:00:01Z",
                "12:34:56-17:30 | 12:34:56-18:00",
                "20:39:06-09:00 | 19:39:07-10:00",
                "09:30:20+09:00 | 08:30:21+08:00",
                "23:59:59+18:00 | 23:59:59+17:30",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-17:30",
                "20:39:07-09:00 | 19:39:06-10:00",
                "09:30:22+09:00 | 08:30:21+08:00",
                "23:59:59+17:30 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-17:30 | 12:34:56-18:00",
                "20:39:06-09:00 | 19:39:07-10:00",
                "09:30:20+09:00 | 08:30:21+08:00",
                "23:59:59+18:00 | 23:59:59+17:30",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:01Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-17:30",
                "20:39:07-09:00 | 19:39:06-10:00",
                "09:30:22+09:00 | 08:30:21+08:00",
                "23:59:59+17:30 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:01Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-17:30",
                "20:39:07-09:00 | 19:39:06-10:00",
                "09:30:22+09:00 | 08:30:21+08:00",
                "23:59:59+17:30 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-17:30 | 12:34:56-18:00",
                "20:39:06-09:00 | 19:39:07-10:00",
                "09:30:20+09:00 | 08:30:21+08:00",
                "23:59:59+18:00 | 23:59:59+17:30",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:00Z      | 00:00:00Z",
                "12:34:56-18:00 | 12:34:56-17:30",
                "20:39:07-09:00 | 19:39:06-10:00",
                "09:30:22+09:00 | 08:30:21+08:00",
                "23:59:59+17:30 | 23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(OffsetTime actual, OffsetTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:01Z",
                "12:34:56-17:30 | 12:34:56-18:00",
                "20:39:06-09:00 | 19:39:07-10:00",
                "09:30:20+09:00 | 08:30:21+08:00",
                "23:59:59+18:00 | 23:59:59+17:30",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than other")
        void test1(OffsetTime actual, OffsetTime expected) {
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
                "00:00:00Z      | 00:00:00Z      | 00:00:00Z",
                "20:09:07-09:30 | 20:39:06-09:00 | 19:39:08-10:00",
                "23:59:58+18:00 | 23:59:50+18:00 | 23:29:59+17:30",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value inclusively")
        void test0(OffsetTime actual, OffsetTime start, OffsetTime end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:01:00Z      | 00:00:00Z      | 00:00:01Z",
                "20:09:05-09:30 | 20:39:06-09:00 | 19:39:08-10:00",
                "23:59:59+18:00 | 23:59:50+18:00 | 23:29:59+17:30",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value inclusively")
        void test1(OffsetTime actual, OffsetTime start, OffsetTime end) {
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
                "00:00:01Z      | 00:00:00Z      | 00:00:02Z",
                "20:09:07-09:30 | 20:39:06-09:00 | 19:39:08-10:00",
                "23:59:58+18:00 | 23:59:50+18:00 | 23:29:59+17:30",
        }, delimiter = '|')
        @DisplayName("passes, when actual is between start value and end value exclusively")
        void test0(OffsetTime actual, OffsetTime start, OffsetTime end) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "00:00:00Z      | 00:00:00Z      | 00:00:00Z",
                "20:09:05-09:30 | 20:39:06-09:00 | 19:39:08-10:00",
                "23:59:59+18:00 | 23:59:50+18:00 | 23:29:59+17:30",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not between start value and end value exclusively")
        void test1(OffsetTime actual, OffsetTime start, OffsetTime end) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isStrictlyBetween(start, end))
                    .withMessageStartingWith("It is expected to be");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSameOffset'")
    class IsSameOffset {
        @Test
        @DisplayName("passes, when actual and other have the same offset")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(OffsetTime.now(offset)).isSameOffset(offset)));
        }

        @Test
        @DisplayName("throws exception, when actual and other don't have the sane offset")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OffsetTime.now(offset))
                            .isSameOffset(ZoneOffset.ofTotalSeconds(-offset.getTotalSeconds())))
                    .withMessageStartingWith("They are expected to have the same offset, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotSameOffset'")
    class IsNotSameOffset {
        @Test
        @DisplayName("passes, when actual and other don't have the sane offset")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(OffsetTime.now(offset))
                            .isNotSameOffset(ZoneOffset.ofTotalSeconds(-offset.getTotalSeconds()))));
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same offset")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OffsetTime.now(offset)).isNotSameOffset(offset))
                    .withMessageStartingWith("They are expected not to have the same offset, but they are."));
        }
    }

}

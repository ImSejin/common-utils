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

package io.github.imsejin.common.assertion.time.chrono;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ChronoZonedDateTimeAssert")
class ChronoZonedDateTimeAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00Z                           | 1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00                      | 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43Z[UTC]                      | 0912-04-23T05:15:43Z[UTC]",
                "4656-12-15T02:25:52Z[GMT]                      | 4656-12-15T02:25:52Z[GMT]",
                "8792-02-02T06:36:50Z[CET]                      | 8792-02-02T06:36:50Z[CET]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]          | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "0544-12-23T21:02:37-07:00[America/Los_Angeles] | 0544-12-23T21:02:37-07:00[America/Los_Angeles]",
                "6580-07-03T04:52:25-07:00[America/Vancouver]   | 6580-07-03T04:52:25-07:00[America/Vancouver]",
                "2148-03-03T04:00:04+01:00[Europe/London]       | 2148-03-03T04:00:04+01:00[Europe/London]",
                "2216-05-20T11:19:43+10:00[Australia/Sydney]    | 2216-05-20T11:19:43+10:00[Australia/Sydney]",
                "5696-01-13T17:22:16+03:00[Africa/Nairobi]      | 5696-01-13T17:22:16+03:00[Africa/Nairobi]",
                "3644-03-07T19:39:07-10:00[Pacific/Honolulu]    | 3644-03-07T19:39:07-10:00[Pacific/Honolulu]",
                "0428-10-10T04:04:16-07:00[US/Pacific]          | 0428-10-10T04:04:16-07:00[US/Pacific]",
                "7244-06-28T17:26:26Z[Etc/UTC]                  | 7244-06-28T17:26:26Z[Etc/UTC]",
                "1048-06-29T13:55:21Z[Etc/GMT+9]                | 1048-06-29T13:55:21Z[Etc/GMT+9]",
                "1616-05-17T01:39:08Z[Etc/GMT-0]                | 1616-05-17T01:39:08Z[Etc/GMT-0]",
                "2022-05-19T23:59:59+18:00                      | 2022-05-19T23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00Z                           | 1592-05-23T00:00:01Z",
                "1918-12-31T12:34:55-18:00                      | 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43Z[UTC]                      | 0912-04-23T05:15:43Z[GMT]",
                "4656-12-15T02:25:52Z[CET]                      | 4656-12-15T02:25:52Z[GMT]",
                "8792-02-02T06:36:50Z[UTC]                      | 8792-02-02T06:36:50Z[CET]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]          | 1999-01-02T08:30:21+09:00[Asia/Seoul]",
                "0544-12-23T21:02:37-07:00[America/Vancouver]   | 0544-12-23T21:02:37-07:00[America/Los_Angeles]",
                "6580-07-03T04:52:25-07:00[America/Los_Angeles] | 6580-07-03T04:52:25-07:00[America/Vancouver]",
                "2148-03-04T04:00:04+01:00[Europe/London]       | 2148-03-03T04:00:04+01:00[Europe/London]",
                "2216-05-20T11:19:43+10:00[Australia/Sydney]    | 2216-06-20T11:19:43+10:00[Australia/Sydney]",
                "5696-01-13T17:23:16+03:00[Africa/Nairobi]      | 5696-01-13T17:22:16+03:00[Africa/Nairobi]",
                "3644-03-07T19:39:07-10:00[US/Pacific]          | 3644-03-07T19:39:07-10:00[Pacific/Honolulu]",
                "0428-10-10T04:04:16-07:00[Pacific/Honolulu]    | 0428-10-10T04:04:16-07:00[US/Pacific]",
                "7244-06-28T17:26:26Z[Etc/GMT-0]                | 7244-06-28T17:26:26Z[Etc/UTC]",
                "1048-06-29T13:55:21Z[Etc/GMT+9]                | 1048-06-29T14:55:21Z[Etc/GMT+9]",
                "1616-05-17T01:39:08Z[Etc/UTC]                  | 1616-05-17T01:39:08Z[Etc/GMT-0]",
                "2022-05-20T23:59:59+18:00                      | 2022-05-19T23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
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
                "1592-05-23T00:00:00Z                           | 1592-05-23T00:00:01Z",
                "1918-12-31T12:34:55-18:00                      | 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43Z[UTC]                      | 0912-04-23T05:15:43Z[GMT]",
                "4656-12-15T02:25:52Z[CET]                      | 4656-12-15T02:25:52Z[GMT]",
                "8792-02-02T06:36:50Z[UTC]                      | 8792-02-02T06:36:50Z[CET]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]          | 1999-01-02T08:30:21+09:00[Asia/Seoul]",
                "0544-12-23T21:02:37-07:00[America/Vancouver]   | 0544-12-23T21:02:37-07:00[America/Los_Angeles]",
                "6580-07-03T04:52:25-07:00[America/Los_Angeles] | 6580-07-03T04:52:25-07:00[America/Vancouver]",
                "2148-03-04T04:00:04+01:00[Europe/London]       | 2148-03-03T04:00:04+01:00[Europe/London]",
                "2216-05-20T11:19:43+10:00[Australia/Sydney]    | 2216-06-20T11:19:43+10:00[Australia/Sydney]",
                "5696-01-13T17:23:16+03:00[Africa/Nairobi]      | 5696-01-13T17:22:16+03:00[Africa/Nairobi]",
                "3644-03-07T19:39:07-10:00[US/Pacific]          | 3644-03-07T19:39:07-10:00[Pacific/Honolulu]",
                "0428-10-10T04:04:16-07:00[Pacific/Honolulu]    | 0428-10-10T04:04:16-07:00[US/Pacific]",
                "7244-06-28T17:26:26Z[Etc/GMT-0]                | 7244-06-28T17:26:26Z[Etc/UTC]",
                "1048-06-29T13:55:21Z[Etc/GMT+9]                | 1048-06-29T14:55:21Z[Etc/GMT+9]",
                "1616-05-17T01:39:08Z[Etc/UTC]                  | 1616-05-17T01:39:08Z[Etc/GMT-0]",
                "2022-05-20T23:59:59+18:00                      | 2022-05-19T23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00Z                           | 1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00                      | 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43Z[UTC]                      | 0912-04-23T05:15:43Z[UTC]",
                "4656-12-15T02:25:52Z[GMT]                      | 4656-12-15T02:25:52Z[GMT]",
                "8792-02-02T06:36:50Z[CET]                      | 8792-02-02T06:36:50Z[CET]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]          | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "0544-12-23T21:02:37-07:00[America/Los_Angeles] | 0544-12-23T21:02:37-07:00[America/Los_Angeles]",
                "6580-07-03T04:52:25-07:00[America/Vancouver]   | 6580-07-03T04:52:25-07:00[America/Vancouver]",
                "2148-03-03T04:00:04+01:00[Europe/London]       | 2148-03-03T04:00:04+01:00[Europe/London]",
                "2216-05-20T11:19:43+10:00[Australia/Sydney]    | 2216-05-20T11:19:43+10:00[Australia/Sydney]",
                "5696-01-13T17:22:16+03:00[Africa/Nairobi]      | 5696-01-13T17:22:16+03:00[Africa/Nairobi]",
                "3644-03-07T19:39:07-10:00[Pacific/Honolulu]    | 3644-03-07T19:39:07-10:00[Pacific/Honolulu]",
                "0428-10-10T04:04:16-07:00[US/Pacific]          | 0428-10-10T04:04:16-07:00[US/Pacific]",
                "7244-06-28T17:26:26Z[Etc/UTC]                  | 7244-06-28T17:26:26Z[Etc/UTC]",
                "1048-06-29T13:55:21Z[Etc/GMT+9]                | 1048-06-29T13:55:21Z[Etc/GMT+9]",
                "1616-05-17T01:39:08Z[Etc/GMT-0]                | 1616-05-17T01:39:08Z[Etc/GMT-0]",
                "2022-05-19T23:59:59+18:00                      | 2022-05-19T23:59:59+18:00",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
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
                "1592-05-23T00:00:00Z                     | 1592-05-23T00:00:01Z",
                "0912-04-23T05:15:43Z[UTC]                | 0912-04-24T05:15:43Z[UTC]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]    | 1999-01-01T08:30:21Z[GMT]",
                "2148-03-03T04:00:04+01:00[Europe/London] | 2148-03-02T20:00:05-08:00[America/Vancouver]",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:01Z                         | 1592-05-23T00:00:00Z",
                "0912-04-23T05:15:43Z[UTC]                    | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21Z[GMT]                    | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "2148-03-02T20:00:05-08:00[America/Vancouver] | 2148-03-03T04:00:04+01:00[Europe/London]",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
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
                "1592-05-23T00:00:00Z                     | 1592-05-23T00:00:01Z",
                "0912-04-23T05:15:43Z[UTC]                | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]    | 1999-01-01T08:30:21Z[GMT]",
                "2148-03-03T04:00:04+01:00[Europe/London] | 2148-03-02T20:00:05-08:00[America/Vancouver]",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:01Z                         | 1592-05-23T00:00:00Z",
                "0912-04-24T05:15:43Z[UTC]                    | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21Z[GMT]                    | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "2148-03-02T20:00:05-08:00[America/Vancouver] | 2148-03-03T04:00:04+01:00[Europe/London]",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
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
                "1592-05-23T00:00:01Z                         | 1592-05-23T00:00:00Z",
                "0912-04-24T05:15:43Z[UTC]                    | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21Z[GMT]                    | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "2148-03-02T20:00:05-08:00[America/Vancouver] | 2148-03-03T04:00:04+01:00[Europe/London]",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00Z                     | 1592-05-23T00:00:01Z",
                "0912-04-23T05:15:43Z[UTC]                | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]    | 1999-01-01T08:30:21Z[GMT]",
                "2148-03-03T04:00:04+01:00[Europe/London] | 2148-03-02T20:00:05-08:00[America/Vancouver]",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
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
                "1592-05-23T00:00:01Z                         | 1592-05-23T00:00:00Z",
                "0912-04-23T05:15:43Z[UTC]                    | 0912-04-23T05:15:43Z[UTC]",
                "1999-01-01T08:30:21Z[GMT]                    | 1999-01-01T08:30:21+09:00[Asia/Seoul]",
                "2148-03-02T20:00:05-08:00[America/Vancouver] | 2148-03-03T04:00:04+01:00[Europe/London]",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00Z                     | 1592-05-23T00:00:01Z",
                "0912-04-23T05:15:43Z[UTC]                | 0912-04-24T05:15:43Z[UTC]",
                "1999-01-01T08:30:21+09:00[Asia/Seoul]    | 1999-01-01T08:30:21Z[GMT]",
                "2148-03-03T04:00:04+01:00[Europe/London] | 2148-03-02T20:00:05-08:00[America/Vancouver]",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than other")
        void test1(ZonedDateTime actual, ZonedDateTime expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSameZone'")
    class IsSameZone {
        @Test
        @DisplayName("passes, when actual and other have the same zone")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone)).isSameZone(zone)));
        }

        @Test
        @DisplayName("throws exception, when actual and other don't have the sane zone")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone))
                            .isSameZone(ZoneOffset.ofTotalSeconds(-zone.getTotalSeconds())))
                    .withMessageStartingWith("They are expected to have the same zone, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotSameZone'")
    class IsNotSameZone {
        @Test
        @DisplayName("passes, when actual and other don't have the sane zone")
        void test0() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone))
                            .isNotSameZone(ZoneOffset.ofTotalSeconds(-zone.getTotalSeconds()))));
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same zone")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(zone -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(ZonedDateTime.now(zone)).isNotSameZone(zone))
                    .withMessageStartingWith("They are expected not to have the same zone, but they are."));
        }
    }

}

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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("OffsetDateTimeAssert")
class OffsetDateTimeAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43-15:30, 0912-04-23T05:15:43-15:30",
                "4656-12-15T02:25:52-12:00, 4656-12-15T02:25:52-12:00",
                "3644-03-07T19:39:07-10:00, 3644-03-07T19:39:07-10:00",
                "0428-10-10T04:04:16-07:00, 0428-10-10T04:04:16-07:00",
                "8792-02-02T06:36:50-00:00, 8792-02-02T06:36:50-00:00",
                "6580-07-03T04:52:25+00:00, 6580-07-03T04:52:25+00:00",
                "2148-03-03T04:00:04+01:00, 2148-03-03T04:00:04+01:00",
                "5696-01-13T17:22:16+03:00, 5696-01-13T17:22:16+03:00",
                "1999-01-01T08:30:21+09:00, 1999-01-01T08:30:21+09:00",
                "2216-05-20T11:19:43+10:00, 2216-05-20T11:19:43+10:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("passes, when actual is equal to other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:01Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56+18:00",
                "0912-04-23T05:15:43-15:30, 0912-04-23T05:15:43-15:00",
                "4656-12-15T02:25:52-11:00, 4656-12-15T02:25:52-12:00",
                "3644-03-07T19:39:07-10:00, 3644-03-07T19:39:08-10:00",
                "0428-10-10T04:04:17-07:00, 0428-10-10T04:04:16-07:00",
                "8792-02-02T06:36:50-00:00, 8792-02-02T07:36:50-00:00",
                "6580-07-04T04:52:25+00:00, 6580-07-03T04:52:25+00:00",
                "2148-03-03T04:00:04+01:00, 2148-04-03T04:00:04+01:00",
                "5697-01-13T17:22:16+03:00, 5696-01-13T17:22:16+03:00",
                "1999-01-01T08:30:21+09:00, 1999-01-01T08:30:21+08:00",
                "2216-05-20T11:19:43+10:00, 2216-05-20T11:19:43+11:00",
                "2022-05-19T23:59:59-18:00, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
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
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:01Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56+18:00",
                "0912-04-23T05:15:43-15:30, 0912-04-23T05:15:43-15:00",
                "4656-12-15T02:25:52-11:00, 4656-12-15T02:25:52-12:00",
                "3644-03-07T19:39:07-10:00, 3644-03-07T19:39:08-10:00",
                "0428-10-10T04:04:17-07:00, 0428-10-10T04:04:16-07:00",
                "8792-02-02T06:36:50-00:00, 8792-02-02T07:36:50-00:00",
                "6580-07-04T04:52:25+00:00, 6580-07-03T04:52:25+00:00",
                "2148-03-03T04:00:04+01:00, 2148-04-03T04:00:04+01:00",
                "5697-01-13T17:22:16+03:00, 5696-01-13T17:22:16+03:00",
                "1999-01-01T08:30:21+09:00, 1999-01-01T08:30:21+08:00",
                "2216-05-20T11:19:43+10:00, 2216-05-20T11:19:43+11:00",
                "2022-05-19T23:59:59-18:00, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("passes, when actual is not equal to other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-18:00",
                "0912-04-23T05:15:43-15:30, 0912-04-23T05:15:43-15:30",
                "4656-12-15T02:25:52-12:00, 4656-12-15T02:25:52-12:00",
                "3644-03-07T19:39:07-10:00, 3644-03-07T19:39:07-10:00",
                "0428-10-10T04:04:16-07:00, 0428-10-10T04:04:16-07:00",
                "8792-02-02T06:36:50-00:00, 8792-02-02T06:36:50-00:00",
                "6580-07-03T04:52:25+00:00, 6580-07-03T04:52:25+00:00",
                "2148-03-03T04:00:04+01:00, 2148-03-03T04:00:04+01:00",
                "5696-01-13T17:22:16+03:00, 5696-01-13T17:22:16+03:00",
                "1999-01-01T08:30:21+09:00, 1999-01-01T08:30:21+09:00",
                "2216-05-20T11:19:43+10:00, 2216-05-20T11:19:43+10:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("throws exception, when actual is equal to other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
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
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:01Z",
                "1918-12-31T12:34:56-17:30, 1918-12-31T12:34:56-18:00",
                "3644-03-07T20:39:06-09:00, 3644-03-07T19:39:07-10:00",
                "1999-01-01T09:30:20+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+17:30",
        })
        @DisplayName("passes, when actual is before than other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-17:30",
                "3644-03-07T20:39:07-09:00, 3644-03-07T19:39:06-10:00",
                "1999-01-01T09:30:22+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+17:30, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
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
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-17:30, 1918-12-31T12:34:56-18:00",
                "3644-03-07T20:39:06-09:00, 3644-03-07T19:39:07-10:00",
                "1999-01-01T09:30:20+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+17:30",
        })
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:01Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-17:30",
                "3644-03-07T20:39:07-09:00, 3644-03-07T19:39:06-10:00",
                "1999-01-01T09:30:22+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+17:30, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("throws exception, when actual is after than other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
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
        @CsvSource({
                "1592-05-23T00:00:01Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-17:30",
                "3644-03-07T20:39:07-09:00, 3644-03-07T19:39:06-10:00",
                "1999-01-01T09:30:22+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+17:30, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("passes, when actual is after than other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-17:30, 1918-12-31T12:34:56-18:00",
                "3644-03-07T20:39:06-09:00, 3644-03-07T19:39:07-10:00",
                "1999-01-01T09:30:20+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+17:30",
        })
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
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
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:00Z",
                "1918-12-31T12:34:56-18:00, 1918-12-31T12:34:56-17:30",
                "3644-03-07T20:39:07-09:00, 3644-03-07T19:39:06-10:00",
                "1999-01-01T09:30:22+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+17:30, 2022-05-19T23:59:59+18:00",
        })
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource({
                "1592-05-23T00:00:00Z,      1592-05-23T00:00:01Z",
                "1918-12-31T12:34:56-17:30, 1918-12-31T12:34:56-18:00",
                "3644-03-07T20:39:06-09:00, 3644-03-07T19:39:07-10:00",
                "1999-01-01T09:30:20+09:00, 1999-01-01T08:30:21+08:00",
                "2022-05-19T23:59:59+18:00, 2022-05-19T23:59:59+17:30",
        })
        @DisplayName("throws exception, when actual is before than other")
        void test1(OffsetDateTime actual, OffsetDateTime expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
                    .isThrownBy(() -> Asserts.that(OffsetDateTime.now(offset)).isSameOffset(offset)));
        }

        @Test
        @DisplayName("throws exception, when actual and other don't have the sane offset")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(1, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OffsetDateTime.now(offset))
                            .isSameOffset(ZoneOffset.ofTotalSeconds(-offset.getTotalSeconds())))
                    .withMessageStartingWith("They are expected to have the same offset, but they aren't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
                    .isThrownBy(() -> Asserts.that(OffsetDateTime.now(offset))
                            .isNotSameOffset(ZoneOffset.ofTotalSeconds(-offset.getTotalSeconds()))));
        }

        @Test
        @DisplayName("throws exception, when actual and other have the same offset")
        void test1() {
            // given
            List<ZoneOffset> params = IntStream.rangeClosed(-18, 18).mapToObj(ZoneOffset::ofHours).collect(toList());

            // expect
            params.forEach(offset -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OffsetDateTime.now(offset)).isNotSameOffset(offset))
                    .withMessageStartingWith("They are expected not to have the same offset, but they are."));
        }
    }

}
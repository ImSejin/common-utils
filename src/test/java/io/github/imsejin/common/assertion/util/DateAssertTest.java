package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.common.Switch;
import org.junit.jupiter.params.converter.ConvertJavaTime;
import org.junit.jupiter.params.converter.JavaTimeFormat;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.RandomJavaTimeSource;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("DateAssert")
class DateAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.789",
                "2022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is equal to other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 2022-05-19T23:59:59.999",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.788",
                "2022-05-19T23:59:59.999 | 1592-05-23T00:00:00.000",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
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
                "1592-05-23T00:00:00.000 | 2022-05-19T23:59:59.999",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.788",
                "2022-05-19T23:59:59.999 | 1592-05-23T00:00:00.000",
        }, delimiter = '|')
        @DisplayName("passes, when actual is not equal to other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isNotEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.789",
                "2022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is equal to other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
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
                "1592-05-23T00:00:00.000 | 1592-05-24T00:00:00.000",
                "1918-12-31T12:34:56.788 | 1918-12-31T12:34:57.789",
                "2022-05-19T23:59:59.999 | 3022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBefore(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.788",
                "3022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than or equal to other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
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
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.790",
                "2022-05-19T23:59:59.999 | 3022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is before than or equal to other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-24T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:57.790 | 1918-12-31T12:34:56.789",
                "3022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is after than other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
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
                "1592-05-24T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:57.790 | 1918-12-31T12:34:56.789",
                "3022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfter(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.790",
                "2022-05-19T23:59:59.999 | 3022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than or equal to other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
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
                "1592-05-23T00:00:00.000 | 1592-05-23T00:00:00.000",
                "1918-12-31T12:34:56.789 | 1918-12-31T12:34:56.788",
                "3022-05-19T23:59:59.999 | 2022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("passes, when actual is after than or equal to other")
        void test0(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatNoException().isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1592-05-23T00:00:00.000 | 1592-05-24T00:00:00.000",
                "1918-12-31T12:34:56.788 | 1918-12-31T12:34:57.789",
                "2022-05-19T23:59:59.999 | 3022-05-19T23:59:59.999",
        }, delimiter = '|')
        @DisplayName("throws exception, when actual is before than other")
        void test1(@JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date actual,
                   @JavaTimeFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") Date expected) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualTo(expected))
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLeapYear'")
    class IsLeapYear {
        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.ON)
        @DisplayName("passes, when actual is leap year")
        void test0(@ConvertJavaTime Instant actual) {
            // given
            Date date = Date.from(actual);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(date).isLeapYear());
        }

        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.OFF)
        @DisplayName("throws exception, when actual is not leap year")
        void test1(@ConvertJavaTime Instant actual) {
            // given
            Date date = Date.from(actual);

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(date).isLeapYear())
                    .withMessageStartingWith("It is expected to be leap year, but it isn't.");
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotLeapYear'")
    class IsNotLeapYear {
        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.OFF)
        @DisplayName("passes, when actual is not leap year")
        void test0(@ConvertJavaTime Instant actual) {
            // given
            Date date = Date.from(actual);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(date).isNotLeapYear());
        }

        @ParameterizedTest
        @RandomJavaTimeSource(leapYear = Switch.ON)
        @DisplayName("throws exception, when actual is leap year")
        void test1(@ConvertJavaTime Instant actual) {
            // given
            Date date = Date.from(actual);

            // expect
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(date).isNotLeapYear())
                    .withMessageStartingWith("It is expected not to be leap year, but it is.");
        }
    }

}

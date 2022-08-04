package io.github.imsejin.common.assertion.time;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("LocalTimeAssert")
class LocalTimeAssertTest {

    @Nested
    @DisplayName("method 'isMidnight'")
    class IsMidnight {
        @Test
        @DisplayName("passes, when actual is midnight")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.MIDNIGHT, LocalTime.MIN, LocalTime.of(0, 0));

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isMidnight()));
        }

        @Test
        @DisplayName("throws exception, when actual is not midnight")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.MIDNIGHT.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(0, 1));

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isMidnight())
                    .withMessageStartingWith("They are expected to be equal, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNoon'")
    class IsNoon {
        @Test
        @DisplayName("passes, when actual is noon")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON, LocalTime.of(12, 0));

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isNoon()));
        }

        @Test
        @DisplayName("throws exception, when actual is not noon")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(12, 0, 1));

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNoon())
                    .withMessageStartingWith("They are expected to be equal, but they aren't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBeforeNoon'")
    class IsBeforeNoon {
        @Test
        @DisplayName("passes, when actual is before than noon")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON.minusNanos(1), LocalTime.MIDNIGHT);

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeNoon()));
        }

        @Test
        @DisplayName("throws exception, when actual is equal to noon or after than noon")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON, LocalTime.NOON.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(12, 0));

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeNoon())
                    .withMessageStartingWith("It is expected to be before than the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isBeforeOrEqualToNoon'")
    class IsBeforeOrEqualToNoon {
        @Test
        @DisplayName("passes, when actual is before than noon or equal to noon")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON, LocalTime.NOON.minusNanos(1), LocalTime.MIDNIGHT);

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualToNoon()));
        }

        @Test
        @DisplayName("throws exception, when actual is after than noon")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(12, 0, 1));

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isBeforeOrEqualToNoon())
                    .withMessageStartingWith("It is expected to be before than or equal to the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAfternoon'")
    class IsAfternoon {
        @Test
        @DisplayName("passes, when actual is afternoon")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(12, 0, 1));

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isAfternoon()));
        }

        @Test
        @DisplayName("throws exception, when actual is not afternoon")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON, LocalTime.NOON.minusNanos(1), LocalTime.MIDNIGHT);

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfternoon())
                    .withMessageStartingWith("It is expected to be after than the other, but it isn't."));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAfterOrEqualToNoon'")
    class IsAfterOrEqualToNoon {
        @Test
        @DisplayName("passes, when actual is equal to afternoon or noon")
        void test0() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON, LocalTime.NOON.plusNanos(1),
                    LocalTime.MAX, LocalTime.of(12, 0));

            // expect
            times.forEach(actual -> assertThatNoException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualToNoon()));
        }

        @Test
        @DisplayName("throws exception, when actual is before than noon")
        void test1() {
            // given
            List<LocalTime> times = Arrays.asList(LocalTime.NOON.minusNanos(1), LocalTime.MIDNIGHT);

            // expect
            times.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAfterOrEqualToNoon())
                    .withMessageStartingWith("It is expected to be after than or equal to the other, but it isn't."));
        }
    }

}

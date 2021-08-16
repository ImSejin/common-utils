package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.ReflectionUtils;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleFunction;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Stopwatch")
class StopwatchTest {

    @Nested
    @DisplayName("method 'isRunning'")
    class IsRunning {
        @Test
        @DisplayName("returns true, when stopwatch is running")
        void test0() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            // expect
            assertThat(stopwatch.isRunning()).isTrue();
        }

        @Test
        @DisplayName("returns false, when stopwatch is not running")
        void test1() {
            assertThat(new Stopwatch().isRunning()).isFalse();
        }
    }

    @Nested
    @DisplayName("method 'hasNeverBeenStopped'")
    class HasNeverBeenStopped {
        @Test
        @DisplayName("returns true, when stopwatch has ever been stopped")
        void test0() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            // expect
            assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
            assertThat(new Stopwatch().hasNeverBeenStopped()).isTrue();
        }

        @Test
        @DisplayName("returns false, when stopwatch has never been stopped")
        void test1() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
            stopwatch.stop();

            // expect
            assertThat(stopwatch.hasNeverBeenStopped()).isFalse();
        }
    }

    @Nested
    @DisplayName("method 'clear'")
    class Clear {
        @Test
        @DisplayName("passes, when stopwatch is not running")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                stopwatch.stop();
                // when
                stopwatch.clear();
                // then
                assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
            });

            assertThatNoException().isThrownBy(() -> {
                // given
                Stopwatch stopwatch = new Stopwatch();
                // when
                stopwatch.clear();
                // then
                assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
            });
        }

        @Test
        @DisplayName("throws exception, when stopwatch is running")
        void test1() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            // expect
            assertThatExceptionOfType(UnsupportedOperationException.class)
                    .isThrownBy(stopwatch::clear)
                    .withMessage("Stopwatch is running. To clear, stop it first");
        }
    }

    @Nested
    @DisplayName("method 'forceClear'")
    class ForceClear {
        @Test
        @DisplayName("passes, when stopwatch is not running")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                // given
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                stopwatch.stop();
                // when
                stopwatch.forceClear();
                // then
                assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
            });

            assertThatNoException().isThrownBy(() -> {
                // given
                Stopwatch stopwatch = new Stopwatch();
                // when
                stopwatch.forceClear();
                // then
                assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
            });
        }

        @Test
        @DisplayName("passes, when stopwatch is running")
        void test1() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            // except
            assertThatNoException().isThrownBy(stopwatch::forceClear);
            assertThat(stopwatch.hasNeverBeenStopped()).isTrue();
        }
    }

    @Nested
    @DisplayName("method 'getTotalTime'")
    class GetTotalTime {
        @Test
        @DisplayName("passes, when stopwatch has ever been stopped")
        void test0() {
            // given
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
            stopwatch.stop();

            // expect
            assertThatNoException().isThrownBy(stopwatch::getTotalTime);
        }

        @Test
        @DisplayName("throws exception, when stopwatch has never been stopped")
        void test1() {
            assertThatThrownBy(new Stopwatch()::getTotalTime)
                    .as("Get total time without any stopping")
                    .hasMessage("Stopwatch has no total time, because it has never been stopped")
                    .isExactlyInstanceOf(UnsupportedOperationException.class);
        }

        @RepeatedTest(10)
        @DisplayName("returns the total time in unit of stopwatch")
        void test2() throws InterruptedException {
            // given
            EnumMap<TimeUnit, DoubleFunction<Double>> units = new EnumMap<>(TimeUnit.class);
            units.put(TimeUnit.NANOSECONDS, n -> n);
            units.put(TimeUnit.MICROSECONDS, n -> n / 1_000);
            units.put(TimeUnit.MILLISECONDS, n -> n / 1_000_000);
            units.put(TimeUnit.SECONDS, n -> n / 1_000_000_000);
            units.put(TimeUnit.MINUTES, n -> n / 1_000_000_000 / 60);
            units.put(TimeUnit.HOURS, n -> n / 1_000_000_000 / 60 / 60);
            units.put(TimeUnit.DAYS, n -> n / 1_000_000_000 / 60 / 60 / 24);

            Stopwatch stopwatch = new Stopwatch();

            // when
            stopwatch.start("first");
            TimeUnit.MILLISECONDS.sleep(75);
            stopwatch.stop();

            stopwatch.start("second");
            TimeUnit.MILLISECONDS.sleep(125);
            stopwatch.stop();

            stopwatch.start("third");
            TimeUnit.MILLISECONDS.sleep(25);
            stopwatch.stop();

            // then
            double ns = stopwatch.getTotalTime();
            for (Map.Entry<TimeUnit, DoubleFunction<Double>> entry : units.entrySet()) {
                TimeUnit timeUnit = entry.getKey();

                stopwatch.setTimeUnit(timeUnit);
                double actual = stopwatch.getTotalTime();

                assertThat(actual)
                        .as("Total time with %s unit", timeUnit.name().toLowerCase())
                        .isCloseTo(entry.getValue().apply(ns), Percentage.withPercentage(99.999999));
                System.out.printf("%s: %s%n", timeUnit, BigDecimal.valueOf(actual).stripTrailingZeros().toPlainString());
            }
            System.out.println("---");
        }
    }

    @Test
    @DisplayName("package-private method 'convertTimeUnit'")
    void convertTimeUnit() throws ReflectiveOperationException {
        // given
        Method method = ReflectionUtils.getDeclaredMethod(Stopwatch.class, "convertTimeUnit",
                double.class, TimeUnit.class, TimeUnit.class);
        method.setAccessible(true);

        // expect
        assertThatConversionFromNanoseconds(method, 102.4);
        assertThatConversionFromMicroseconds(method, 51.2);
        assertThatConversionFromMilliseconds(method, 25.6);
        assertThatConversionFromSeconds(method, 12.8);
        assertThatConversionFromMinutes(method, 6.4);
        assertThatConversionFromHours(method, 3.2);
        assertThatConversionFromDays(method, 1.6);
    }

    @Test
    @Disabled
    void getSummary() throws InterruptedException {
        // given
        Stopwatch stopwatch = new Stopwatch();

        // when
        stopwatch.start();
        TimeUnit.MILLISECONDS.sleep(250);
        stopwatch.stop();

        // then
        for (TimeUnit timeUnit : TimeUnit.values()) {
            stopwatch.setTimeUnit(timeUnit);
            System.out.println(stopwatch.getSummary());
        }
    }

    @Test
    @Disabled
    void getStatistics() throws InterruptedException {
        // given
        Stopwatch stopwatch = new Stopwatch();

        // when
        stopwatch.start("the first");
        TimeUnit.NANOSECONDS.sleep(6_257_148_09L);
        stopwatch.stop();

        stopwatch.start("the second");
        TimeUnit.MICROSECONDS.sleep(843_024);
        stopwatch.stop();

        stopwatch.start("the third");
        TimeUnit.MICROSECONDS.sleep(150_973);
        stopwatch.stop();

        stopwatch.start("the fourth");
        TimeUnit.MICROSECONDS.sleep(1_284_747);
        stopwatch.stop();

        // then
        for (TimeUnit timeUnit : TimeUnit.values()) {
            stopwatch.setTimeUnit(timeUnit);
            System.out.println(stopwatch.getStatistics());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static void assertThatConversionFromNanoseconds(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount / 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount / 1_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount / 1_000_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount / 1_000_000_000 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount / 1_000_000_000 / 60 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 1_000_000_000 / 60 / 60 / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromMicroseconds(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount / 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount / 1_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount / 1_000_000 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount / 1_000_000 / 60 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 1_000_000 / 60 / 60 / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromMilliseconds(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount * 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount / 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount / 1_000 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount / 1_000 / 60 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 1_000 / 60 / 60 / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromSeconds(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount * 1_000_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount * 1_000).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount / 60 / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 60 / 60 / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromMinutes(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000_000_000 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount * 1_000_000 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount * 1_000 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount / 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 60 / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromHours(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000_000_000 * 60 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount * 1_000_000 * 60 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount * 1_000 * 60 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount * 60 * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount * 60).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount / 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, TimeUnit.DAYS), per);
    }

    private static void assertThatConversionFromDays(Method convertTimeUnit, double amount) throws ReflectiveOperationException {
        Percentage per = Percentage.withPercentage(99.999999);
        // To nanoseconds.
        assertThat(amount * 1_000_000_000 * 60 * 60 * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.NANOSECONDS), per);
        // To microseconds.
        assertThat(amount * 1_000_000 * 60 * 60 * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.MICROSECONDS), per);
        // To milliseconds.
        assertThat(amount * 1_000 * 60 * 60 * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.MILLISECONDS), per);
        // To seconds.
        assertThat(amount * 60 * 60 * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.SECONDS), per);
        // To minutes.
        assertThat(amount * 60 * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.MINUTES), per);
        // To hours.
        assertThat(amount * 24).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.HOURS), per);
        // To days.
        assertThat(amount).isCloseTo((double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, TimeUnit.DAYS), per);
    }

}

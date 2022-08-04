package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.ReflectionUtils;
import lombok.SneakyThrows;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
            stopwatch.start("Let's start %s", Stopwatch.class.getSimpleName());

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

            // expect
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
            for (TimeUnit timeUnit : TimeUnit.values()) {
                stopwatch.setTimeUnit(timeUnit);
                double actual = stopwatch.getTotalTime();

                assertThat(actual)
                        .as("Total time with %s unit", timeUnit.name().toLowerCase())
                        .isCloseTo(units.get(timeUnit).apply(ns), Percentage.withPercentage(1.0E-10));
                System.out.printf("%s %s%n", BigDecimal.valueOf(actual).stripTrailingZeros().toPlainString(),
                        timeUnit.name().toLowerCase());
            }
            System.out.println("---");
        }
    }

    @RepeatedTest(100)
    @DisplayName("package-private method 'convertTimeUnit'")
    void convertTimeUnit() {
        // given
        Method method = ReflectionUtils.getDeclaredMethod(Stopwatch.class, "convertTimeUnit",
                double.class, TimeUnit.class, TimeUnit.class);
        method.setAccessible(true);

        // expect
        Random random = new Random();
        assertThatConversionFromNanoseconds(method, random.nextDouble());
        assertThatConversionFromMicroseconds(method, random.nextDouble());
        assertThatConversionFromMilliseconds(method, random.nextDouble());
        assertThatConversionFromSeconds(method, random.nextDouble());
        assertThatConversionFromMinutes(method, random.nextDouble());
        assertThatConversionFromHours(method, random.nextDouble());
        assertThatConversionFromDays(method, random.nextDouble());
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

    // -------------------------------------------------------------------------------------------------

    @SneakyThrows
    private static void assertThatConversionFromNanoseconds(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n);
        compensators.put(TimeUnit.MICROSECONDS, n -> n / 1_000);
        compensators.put(TimeUnit.MILLISECONDS, n -> n / 1_000_000);
        compensators.put(TimeUnit.SECONDS, n -> n / 1_000_000_000);
        compensators.put(TimeUnit.MINUTES, n -> n / 1_000_000_000 / 60);
        compensators.put(TimeUnit.HOURS, n -> n / 1_000_000_000 / 60 / 60);
        compensators.put(TimeUnit.DAYS, n -> n / 1_000_000_000 / 60 / 60 / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.NANOSECONDS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromMicroseconds(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000);
        compensators.put(TimeUnit.MICROSECONDS, n -> n);
        compensators.put(TimeUnit.MILLISECONDS, n -> n / 1_000);
        compensators.put(TimeUnit.SECONDS, n -> n / 1_000_000);
        compensators.put(TimeUnit.MINUTES, n -> n / 1_000_000 / 60);
        compensators.put(TimeUnit.HOURS, n -> n / 1_000_000 / 60 / 60);
        compensators.put(TimeUnit.DAYS, n -> n / 1_000_000 / 60 / 60 / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.MICROSECONDS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromMilliseconds(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000_000);
        compensators.put(TimeUnit.MICROSECONDS, n -> n * 1_000);
        compensators.put(TimeUnit.MILLISECONDS, n -> n);
        compensators.put(TimeUnit.SECONDS, n -> n / 1_000);
        compensators.put(TimeUnit.MINUTES, n -> n / 1_000 / 60);
        compensators.put(TimeUnit.HOURS, n -> n / 1_000 / 60 / 60);
        compensators.put(TimeUnit.DAYS, n -> n / 1_000 / 60 / 60 / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.MILLISECONDS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromSeconds(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000_000_000);
        compensators.put(TimeUnit.MICROSECONDS, n -> n * 1_000_000);
        compensators.put(TimeUnit.MILLISECONDS, n -> n * 1_000);
        compensators.put(TimeUnit.SECONDS, n -> n);
        compensators.put(TimeUnit.MINUTES, n -> n / 60);
        compensators.put(TimeUnit.HOURS, n -> n / 60 / 60);
        compensators.put(TimeUnit.DAYS, n -> n / 60 / 60 / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.SECONDS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromMinutes(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000_000_000 * 60);
        compensators.put(TimeUnit.MICROSECONDS, n -> n * 1_000_000 * 60);
        compensators.put(TimeUnit.MILLISECONDS, n -> n * 1_000 * 60);
        compensators.put(TimeUnit.SECONDS, n -> n * 60);
        compensators.put(TimeUnit.MINUTES, n -> n);
        compensators.put(TimeUnit.HOURS, n -> n / 60);
        compensators.put(TimeUnit.DAYS, n -> n / 60 / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.MINUTES, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromHours(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000_000_000 * 60 * 60);
        compensators.put(TimeUnit.MICROSECONDS, n -> n * 1_000_000 * 60 * 60);
        compensators.put(TimeUnit.MILLISECONDS, n -> n * 1_000 * 60 * 60);
        compensators.put(TimeUnit.SECONDS, n -> n * 60 * 60);
        compensators.put(TimeUnit.MINUTES, n -> n * 60);
        compensators.put(TimeUnit.HOURS, n -> n);
        compensators.put(TimeUnit.DAYS, n -> n / 24);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.HOURS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

    @SneakyThrows
    private static void assertThatConversionFromDays(Method convertTimeUnit, double amount) {
        EnumMap<TimeUnit, DoubleFunction<Double>> compensators = new EnumMap<>(TimeUnit.class);
        compensators.put(TimeUnit.NANOSECONDS, n -> n * 1_000_000_000 * 60 * 60 * 24);
        compensators.put(TimeUnit.MICROSECONDS, n -> n * 1_000_000 * 60 * 60 * 24);
        compensators.put(TimeUnit.MILLISECONDS, n -> n * 1_000 * 60 * 60 * 24);
        compensators.put(TimeUnit.SECONDS, n -> n * 60 * 60 * 24);
        compensators.put(TimeUnit.MINUTES, n -> n * 60 * 24);
        compensators.put(TimeUnit.HOURS, n -> n * 24);
        compensators.put(TimeUnit.DAYS, n -> n);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            DoubleFunction<Double> compensate = compensators.get(timeUnit);

            double converted = (double) convertTimeUnit.invoke(null, amount, TimeUnit.DAYS, timeUnit);
            assertThat(compensate.apply(amount)).isCloseTo(converted, Percentage.withPercentage(1.0E-10));
        }
    }

}

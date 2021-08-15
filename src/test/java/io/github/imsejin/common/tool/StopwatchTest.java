package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.MathUtils;
import org.junit.jupiter.api.*;

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
            TimeUnit.MILLISECONDS.sleep(100);
            stopwatch.stop();

            stopwatch.start("second");
            TimeUnit.MILLISECONDS.sleep(200);
            stopwatch.stop();

            stopwatch.start("third");
            TimeUnit.MILLISECONDS.sleep(50);
            stopwatch.stop();

            // then
            double ns = MathUtils.floor(stopwatch.getTotalTime(), 6);
            for (Map.Entry<TimeUnit, DoubleFunction<Double>> entry : units.entrySet()) {
                TimeUnit timeUnit = entry.getKey();

                double expected = MathUtils.floor(entry.getValue().apply(ns), 6);
                stopwatch.setTimeUnit(timeUnit);
                assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                        .as("Total time with %s unit", timeUnit.name().toLowerCase())
                        .isEqualTo(expected);
                System.out.printf("%s: %s%n", timeUnit, BigDecimal.valueOf(expected).stripTrailingZeros().toPlainString());
            }
        }
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

    @Test
    @Disabled
    void convertTimeUnit1() {
        // given
        long amount = 2000;

        // when: ms => sec
        long convert = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        System.out.println(convert);
        long converted = amount / convert;

        // then
        System.out.println(converted + " sec");
    }

    @Test
    @Disabled
    void convertTimeUnit2() {
        // given
        long amount = 2;

        // when: sec => ms
        double converted = amount * TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);

        // then
        System.out.println(converted + " ms");
    }

}

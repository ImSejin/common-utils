package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.MathUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void getTotalTime() throws InterruptedException {
        // given
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start("the first");

        // when
        TimeUnit.MILLISECONDS.sleep(100);
        stopwatch.stop();

        stopwatch.start("the second");
        TimeUnit.MILLISECONDS.sleep(350);
        stopwatch.stop();
        double totalTime = MathUtils.floor(stopwatch.getTotalTime(), 6);

        // then
        assertThat(totalTime)
                .as("#1 Total time with nanoseconds unit")
                .isEqualTo(stopwatch.getTotalTime());
        System.out.println(BigDecimal.valueOf(stopwatch.getTotalTime()).stripTrailingZeros().toPlainString() + " ns");

        stopwatch.setTimeUnit(TimeUnit.MICROSECONDS);
        double us = MathUtils.floor(totalTime / 1_000, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#2 Total time with microseconds unit")
                .isEqualTo(us);
        System.out.println(BigDecimal.valueOf(us).stripTrailingZeros().toPlainString() + " μs");

        stopwatch.setTimeUnit(TimeUnit.MILLISECONDS);
        double ms = MathUtils.floor(totalTime / 1_000_000, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#3 Total time with milliseconds unit")
                .isEqualTo(ms);
        System.out.println(BigDecimal.valueOf(ms).stripTrailingZeros().toPlainString() + " ms");

        stopwatch.setTimeUnit(TimeUnit.SECONDS);
        double sec = MathUtils.floor(totalTime / 1_000_000_000, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#4 Total time with seconds unit")
                .isEqualTo(sec);
        System.out.println(BigDecimal.valueOf(sec).stripTrailingZeros().toPlainString() + " sec");

        stopwatch.setTimeUnit(TimeUnit.MINUTES);
        double min = MathUtils.floor(totalTime / 1_000_000_000 / 60, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#5 Total time with minutes unit")
                .isEqualTo(min);
        System.out.println(BigDecimal.valueOf(min).stripTrailingZeros().toPlainString() + " min");

        stopwatch.setTimeUnit(TimeUnit.HOURS);
        double hrs = MathUtils.floor(totalTime / 1_000_000_000 / 60 / 60, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#6 Total time with hours unit")
                .isEqualTo(hrs);
        System.out.println(BigDecimal.valueOf(hrs).stripTrailingZeros().toPlainString() + " hrs");

        stopwatch.setTimeUnit(TimeUnit.DAYS);
        double days = MathUtils.floor(totalTime / 1_000_000_000 / 60 / 60 / 24, 6);
        assertThat(MathUtils.floor(stopwatch.getTotalTime(), 6))
                .as("#7 Total time with days unit")
                .isEqualTo(days);
        System.out.println(BigDecimal.valueOf(days).stripTrailingZeros().toPlainString() + " days");
    }

    @Test
    @DisplayName("getTotalTime() + hasNeverBeenStopped()")
    void getTotalTimeWithException() {
        // given
        Stopwatch stopwatch = new Stopwatch();

        // when & then
        assertThatThrownBy(stopwatch::getTotalTime)
                .as("Get total time without any stopping")
                .hasMessage("Stopwatch has never been stopped")
                .isExactlyInstanceOf(RuntimeException.class);
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

package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.MathUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class StopwatchTest {

    @Test
    @SneakyThrows
    public void getTotalTime() {
        // given
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start("the first");

        // when
        TimeUnit.MILLISECONDS.sleep(100);
        stopwatch.stop();

        stopwatch.start("the second");
        TimeUnit.MILLISECONDS.sleep(250);
        stopwatch.stop();
        double totalTime = stopwatch.getTotalTime();

        // then
        assertThat(totalTime)
                .as("total time with nanoseconds unit")
                .isEqualTo(stopwatch.getTotalTime());
        System.out.println(BigDecimal.valueOf(stopwatch.getTotalTime()).toPlainString() + " ns");

        stopwatch.setTimeUnit(TimeUnit.MICROSECONDS);
        double us = MathUtils.floor(totalTime / 1_000, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with microseconds unit")
                .isEqualTo(us);
        System.out.println(BigDecimal.valueOf(us).toPlainString() + " μs");

        stopwatch.setTimeUnit(TimeUnit.MILLISECONDS);
        double ms = MathUtils.floor(totalTime / 1_000_000, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with milliseconds unit")
                .isEqualTo(ms);
        System.out.println(BigDecimal.valueOf(ms).toPlainString() + " ms");

        stopwatch.setTimeUnit(TimeUnit.SECONDS);
        double sec = MathUtils.floor(totalTime / 1_000_000_000, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with seconds unit")
                .isEqualTo(sec);
        System.out.println(BigDecimal.valueOf(sec).toPlainString() + " sec");

        stopwatch.setTimeUnit(TimeUnit.MINUTES);
        double min = MathUtils.floor(totalTime / 1_000_000_000 / 60, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with minutes unit")
                .isEqualTo(min);
        System.out.println(BigDecimal.valueOf(min).toPlainString() + " min");

        stopwatch.setTimeUnit(TimeUnit.HOURS);
        double hrs = MathUtils.floor(totalTime / 1_000_000_000 / 60 / 60, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with hours unit")
                .isEqualTo(hrs);
        System.out.println(BigDecimal.valueOf(hrs).toPlainString() + " hrs");

        stopwatch.setTimeUnit(TimeUnit.DAYS);
        double days = MathUtils.floor(totalTime / 1_000_000_000 / 60 / 60 / 24, 6);
        assertThat(stopwatch.getTotalTime())
                .as("total time with days unit")
                .isEqualTo(days);
        System.out.println(BigDecimal.valueOf(days).toPlainString() + " days");
    }

    @Test
    @SneakyThrows
    public void getSummary() {
        // given
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        // when
        TimeUnit.MILLISECONDS.sleep(250);
        stopwatch.stop();

        // then
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.MICROSECONDS);
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.MILLISECONDS);
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.SECONDS);
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.MINUTES);
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.HOURS);
        System.out.println(stopwatch.getSummary());
        stopwatch.setTimeUnit(TimeUnit.DAYS);
        System.out.println(stopwatch.getSummary());
    }

    @Test
    @SneakyThrows
    public void getStatistics() {
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
        stopwatch.setTimeUnit(TimeUnit.NANOSECONDS);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.MICROSECONDS);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.MILLISECONDS);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.SECONDS);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.MINUTES);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.HOURS);
        System.out.println(stopwatch.getStatistics());

        stopwatch.setTimeUnit(TimeUnit.DAYS);
        System.out.println(stopwatch.getStatistics());
    }

    @Test
    public void convertTimeUnit1() {
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
    public void convertTimeUnit2() {
        // given
        long amount = 2;

        // when: sec => ms
        double converted = amount * TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);

        // then
        System.out.println(converted + " ms");
    }

}

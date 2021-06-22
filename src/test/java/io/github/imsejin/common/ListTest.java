package io.github.imsejin.common;

import io.github.imsejin.common.tool.Stopwatch;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class ListTest {

    @Test
    void appendToArrayList() {
        // given
        List<Integer> list = new ArrayList<>();
        Stopwatch stopwatch = new Stopwatch(TimeUnit.SECONDS);
        stopwatch.start();

        // when
        for (int i = 0; i < 1_000_000; i++) {
            list.add(i);
        }

        // then
        stopwatch.stop();
        System.out.println(stopwatch.getStatistics());
    }

    @Test
    void appendToLinkedList() {
        // given
        List<Integer> list = new LinkedList<>();
        Stopwatch stopwatch = new Stopwatch(TimeUnit.SECONDS);
        stopwatch.start();

        // when
        for (int i = 0; i < 1_000_000; i++) {
            list.add(i);
        }

        // then
        stopwatch.stop();
        System.out.println(stopwatch.getStatistics());
    }

}

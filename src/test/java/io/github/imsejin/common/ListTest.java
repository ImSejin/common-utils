package io.github.imsejin.common;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {

    @Test
    public void appendToArrayList() {
        // given
        List<Integer> list = new ArrayList<>();
        long startTime = System.nanoTime();

        // when
        for (int i = 0; i < 10_000_000; i++) {
            list.add(i);
        }

        // then
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println((elapsedTime / 1_000_000.0) + " ms");
    }

    @Test
    public void appendToLinkedList() {
        // given
        List<Integer> list = new LinkedList<>();
        long startTime = System.nanoTime();

        // when
        for (int i = 0; i < 10_000_000; i++) {
            list.add(i);
        }

        // then
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println((elapsedTime / 1_000_000.0) + " ms");
    }

}

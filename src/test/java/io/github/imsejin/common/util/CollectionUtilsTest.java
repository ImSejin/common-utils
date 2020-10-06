package io.github.imsejin.common.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionUtilsTest {

    @Test
    public void toMap() {
        // given
        List<String> list = Arrays.asList("A", "B", "C");
        // when
        Map<Integer, String> map1 = CollectionUtils.toMap(list);
        // then
        map1.forEach((k, v) -> assertThat(v, is(list.get(k))));

        // given
        Set<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));
        // when
        Map<Integer, String> map2 = CollectionUtils.toMap(set);
        // then
        map2.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362, 8_702_145, 12_345_679})
    public void partition(int size) {
        // given
        List<Integer> integers = IntStream.range(0, 12_345_678).boxed().collect(Collectors.toList());

        // when
        List<List<Integer>> partition = CollectionUtils.partition(integers, size);

        // then
        int partitionSize = partition.size();
        int integerSize = integers.size();
        boolean modExists = Math.floorMod(integerSize, size) > 0;

        for (int i = 0; i < partitionSize; i++) {
            List<Integer> list = partition.get(i);

            if (modExists && i == partitionSize - 1) {
                assertEquals(list.size(), integerSize % size);
            } else {
                assertEquals(list.size(), size);
            }
        }

        System.out.println("partition(" + 12_345_678 + ", " + size + ").size() == " + partitionSize);
    }

}

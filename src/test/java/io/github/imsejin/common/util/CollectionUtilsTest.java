package io.github.imsejin.common.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void partitionBySize(int chunkSize) {
        // given
        int range = 12_345_678;
        List<Integer> integers = IntStream.range(0, range).boxed().collect(Collectors.toList());

        // when
        List<List<Integer>> partition = CollectionUtils.partitionBySize(integers, chunkSize);

        // then
        int partitionSize = partition.size();
        int integerSize = integers.size();
        boolean modExists = Math.floorMod(integerSize, chunkSize) > 0;

        for (int i = 0; i < partitionSize; i++) {
            List<Integer> list = partition.get(i);

            if (modExists && i == partitionSize - 1) {
                assertEquals(list.size(), integerSize % chunkSize);
            } else {
                assertEquals(list.size(), chunkSize);
            }
        }

        System.out.println("partitionBySize(" + range + ", " + chunkSize + ").size() == " + partitionSize);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362, 8_702_145, 12_345_678})
    public void partitionByCount(int count) {
        // given
        int range = 12_345_678;
        List<Integer> integers = IntStream.range(0, range).boxed().collect(Collectors.toList());
        int originSize = integers.size();

        // when
        List<List<Integer>> outer = CollectionUtils.partitionByCount(integers, count);

        // then
        assertEquals(outer.size(), count);
        List<List<Integer>> maybeExceptLast = Math.floorMod(originSize, count) > 0
                ? outer.subList(0, count - 2)
                : outer;
        assertEquals(maybeExceptLast.stream().mapToInt(List::size).max().getAsInt(),
                maybeExceptLast.stream().mapToInt(List::size).min().getAsInt());

        System.out.println("partitionByCount(" + range + ", " + count + ").size() == " + count);
        System.out.println("lastInnerList.size(): " + outer.get(outer.size() - 1).size());
        System.out.println("others.size(): " + outer.get(0).size());
    }

}

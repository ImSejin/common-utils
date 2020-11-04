package io.github.imsejin.common.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"lorem", "ipsum", "is", "simply", "dummy", "text",
            "of", "the", "printing", "and", "typesetting", "industry"})
    public void toMap(String word) {
        // given
        List<Character> list = word.chars().mapToObj(c -> (char) c).collect(toList());

        // when
        Map<Integer, Character> map = CollectionUtils.toMap(list);

        // then
        map.entrySet().stream()
                .peek(it -> System.out.printf("%d: %s\n", it.getKey(), it.getValue()))
                .forEach(it -> assertThat(it.getValue())
                        .as("Are origin list's element and converted map's value the same?")
                        .isEqualTo(list.get(it.getKey())));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362, 8_702_145, 12_345_679})
    public void partitionBySize(int chunkSize) {
        // given
        int range = 12_345_678;
        List<Integer> integers = IntStream.range(0, range).boxed().collect(toList());
        int originSize = integers.size();

        // when
        List<List<Integer>> outer = CollectionUtils.partitionBySize(integers, chunkSize);

        // then
        int outerSize = outer.size();
        boolean modExists = Math.floorMod(originSize, chunkSize) > 0;

        for (int i = 0; i < outerSize; i++) {
            List<Integer> inner = outer.get(i);

            if (modExists && i == outerSize - 1) {
                assertThat(inner.size())
                        .as("#1 If remainder exists, last sub list's size is equal to it")
                        .isEqualTo(Math.floorMod(originSize, chunkSize));
            } else {
                assertThat(inner.size())
                        .as("#1 All sub lists except the last are equal to chuck's size")
                        .isEqualTo(chunkSize);
            }
        }

        List<List<Integer>> maybeExceptLast = Math.floorMod(originSize, chunkSize) > 0 && Math.floorDiv(originSize, chunkSize) > 0
                ? outer.subList(0, outerSize - 2)
                : outer;
        assertThat(maybeExceptLast.stream().mapToInt(List::size).reduce(Integer::max))
                .as("#2 Are all sizes of inner lists except last the same?")
                .isEqualTo(maybeExceptLast.stream().mapToInt(List::size).reduce(Integer::min));
        assertThat(outer.stream().mapToInt(List::size).sum())
                .as("#3 Are sum of outer list' size and origin list's size the same?")
                .isEqualTo(range);

        System.out.printf("partitionBySize(%d, %d).size(): %d\n", range, chunkSize, chunkSize);
        System.out.printf("lastInnerList.size(): %d\n", outer.get(outer.size() - 1).size());
        System.out.printf("others.size(): %d\n", outer.get(0).size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 33, 369, 5120, 17_726, 655_362, 8_702_145, 12_345_678})
    public void partitionByCount(int count) {
        // given
        int range = 12_345_678;
        List<Integer> integers = IntStream.range(0, range).boxed().collect(toList());
        int originSize = integers.size();

        // when
        List<List<Integer>> outer = CollectionUtils.partitionByCount(integers, count);

        // then
        assertThat(outer.size())
                .as("#1 Are outer list's size and parameter 'count' the same?")
                .isEqualTo(count);
        List<List<Integer>> maybeExceptLast = Math.floorMod(originSize, count) > 0
                ? outer.subList(0, count - 2)
                : outer;
        assertThat(maybeExceptLast.stream().mapToInt(List::size).reduce(Integer::max))
                .as("#2 Are all sizes of inner lists except last the same?")
                .isEqualTo(maybeExceptLast.stream().mapToInt(List::size).reduce(Integer::min));
        assertThat(outer.stream().mapToInt(List::size).sum())
                .as("#3 Are sum of outer list' size and origin list's size the same?")
                .isEqualTo(range);

        System.out.printf("partitionByCount(%d, %d).size(): %d\n", range, count, count);
        System.out.printf("lastInnerList.size(): %d\n", outer.get(outer.size() - 1).size());
        System.out.printf("others.size(): %d\n", outer.get(0).size());
    }

    @Test
    public void median() {
        // given
        long[] longs = {Long.MAX_VALUE, Long.MIN_VALUE, 0, 2, 33, 369, 5120, 17_726,
                Integer.MIN_VALUE, Integer.MAX_VALUE, 8_702_145, 12_345_678};

        // when
        double actual = CollectionUtils.median(longs);

        // then
        assertThat(actual)
                .as("Gets median value in array")
                .isEqualTo(2744.5);
    }

}

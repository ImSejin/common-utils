package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Collector utilities
 *
 * @see Collector
 * @see Collectors
 */
public final class CollectorUtils {

    @ExcludeFromGeneratedJacocoReport
    private CollectorUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Returns a collector which compares property of each element with extractor and sorts by their rank.
     *
     * @param <T> type of input element
     * @return ranking map
     */
    public static <T extends Comparable<T>> Collector<T, ?, SortedMap<Integer, List<T>>> ranking() {
        return ranking(Function.identity(), T::compareTo);
    }

    /**
     * Returns a collector which compares property of each element with extractor and sorts by their rank.
     *
     * @param propertyExtractor  extractor to get property of the element
     * @param propertyComparator comparator to the property for ranking
     * @param <T>                type of input element
     * @param <R>                type of extracted property from the element
     * @return ranking map
     * @see <a href="https://github.com/mtorchiano/JavaUtilities">it.polito.utility.stream.Ranking</a>
     */
    public static <T, R> Collector<T, ?, SortedMap<Integer, List<T>>> ranking(
            Function<T, R> propertyExtractor, Comparator<R> propertyComparator) {
        return collectingAndThen(
                groupingBy(propertyExtractor, () -> new TreeMap<>(propertyComparator), toList()),
                map -> map.entrySet().stream().collect(
                        () -> new TreeMap<Integer, List<T>>() {
                            @Override
                            public String toString() {
                                return this.entrySet().stream()
                                        .flatMap(e -> e.getValue().stream().map(v -> e.getKey().toString() + " - " + v.toString() + " (" + propertyExtractor.apply(v) + ")"))
                                        .collect(joining("\n"));
                            }
                        },
                        (ranks, entry) -> {
                            if (ranks.isEmpty()) {
                                ranks.put(1, entry.getValue());
                                return;
                            }

                            Integer lastRank = ranks.lastKey();
                            List<T> items = ranks.get(lastRank);
                            ranks.put(lastRank + items.size(), entry.getValue());
                        },
                        (left, right) -> {
                            int lastRank = left.lastKey();
                            List<T> leftItems = left.get(lastRank);

                            Integer firstKey = right.firstKey();
                            List<T> rightItems = right.get(firstKey);

                            R leftProp = propertyExtractor.apply(leftItems.get(0));
                            R rightProp = propertyExtractor.apply(rightItems.get(0));

                            if (leftProp == rightProp) {
                                leftItems.addAll(rightItems);
                                right.remove(firstKey);
                            }

                            int offset = lastRank + leftItems.size() - 1;
                            right.forEach((rank, items) -> left.put(offset + rank, items));
                        }
                )
        );
    }

}

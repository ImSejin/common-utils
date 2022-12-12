package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
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
                        () -> new RankMap<>(propertyExtractor),
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
                            List<T> leftItems = left.get(left.lastKey());
                            List<T> rightItems = right.get(right.firstKey());

                            R leftProp = propertyExtractor.apply(leftItems.get(0));
                            R rightProp = propertyExtractor.apply(rightItems.get(0));

                            if (leftProp == rightProp) {
                                leftItems.addAll(rightItems);
                                right.remove(right.firstKey());
                            }

                            int offset = left.lastKey() + leftItems.size() - 1;
                            right.forEach((rank, items) -> left.put(offset + rank, items));
                        }
                )
        );
    }

    /**
     * Returns a collector which compares property of each element with extractor and sorts by their dense rank.
     *
     * @param <T> type of input element
     * @return ranking map
     */
    public static <T extends Comparable<T>> Collector<T, ?, SortedMap<Integer, List<T>>> denseRanking() {
        return denseRanking(Function.identity(), T::compareTo);
    }

    /**
     * Returns a collector which compares property of each element with extractor and sorts by their dense rank.
     *
     * @param propertyExtractor  extractor to get property of the element
     * @param propertyComparator comparator to the property for ranking
     * @param <T>                type of input element
     * @param <R>                type of extracted property from the element
     * @return ranking map
     */
    public static <T, R> Collector<T, ?, SortedMap<Integer, List<T>>> denseRanking(
            Function<T, R> propertyExtractor, Comparator<R> propertyComparator) {
        return collectingAndThen(
                groupingBy(propertyExtractor, () -> new TreeMap<>(propertyComparator), toList()),
                map -> map.entrySet().stream().collect(
                        () -> new RankMap<>(propertyExtractor),
                        (ranks, entry) -> {
                            if (ranks.isEmpty()) {
                                ranks.put(1, entry.getValue());
                                return;
                            }

                            ranks.put(ranks.lastKey() + 1, entry.getValue());
                        },
                        (left, right) -> {
                            List<T> rightItems = right.get(right.firstKey());
                            List<T> leftItems = left.get(left.lastKey());

                            R rightProp = propertyExtractor.apply(rightItems.get(0));
                            R leftProp = propertyExtractor.apply(leftItems.get(0));

                            if (leftProp == rightProp) {
                                leftItems.addAll(rightItems);
                                right.remove(right.firstKey());
                            }

                            int offset = left.lastKey() - 1;
                            right.forEach((rank, items) -> left.put(offset + rank, items));
                        }
                )
        );
    }

    // -------------------------------------------------------------------------------------------------

    private static class RankMap<T> extends TreeMap<Integer, List<T>> {
        private final Function<T, ?> propertyExtractor;

        private RankMap(Function<T, ?> propertyExtractor) {
            this.propertyExtractor = propertyExtractor;
        }

        @Override
        @ExcludeFromGeneratedJacocoReport
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<Integer, List<T>> e : entrySet()) {
                List<T> values = e.getValue();

                for (T value : values) {
                    sb.append(e.getKey());
                    sb.append(" - ");
                    sb.append(value);
                    sb.append(" (");
                    sb.append(this.propertyExtractor.apply(value));
                    sb.append(')');
                }

                sb.append('\n');
            }

            return sb.toString();
        }
    }

}

package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;

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

    public static <T extends Comparable<T>> Collector<T, ?, List<Entry<Integer, T>>> ranking() {
        return ranking(T::compareTo);
    }

    public static <T> Collector<T, ?, List<Entry<Integer, T>>> ranking(Comparator<? super T> comparator) {
        return new CollectorImpl<>(
                (Supplier<List<Pair<Integer, T>>>) ArrayList::new,
                (pairs, cur) -> {
                    pairs.add(new Pair<>(null, cur));

                    for (Pair<Integer, T> p1 : pairs) {
                        // Initializes the first rank as it is temporary.
                        p1.setLeft(1);

                        for (Pair<Integer, T> p2 : pairs) {
                            int comparison = comparator.compare(p1.getRight(), p2.getRight());
                            if (comparison > 0) {
                                p1.setLeft(p1.getLeft() + 1);
                            }
                        }
                    }
                },
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                pairs -> {
                    List<Entry<Integer, T>> ranks = new ArrayList<>(pairs.size());
                    for (Pair<Integer, T> pair : pairs) {
                        ranks.add(new SimpleEntry<>(pair.left, pair.right));
                    }

                    // Sorts by rank.
                    ranks.sort(Entry.comparingByKey());

                    return ranks;
                },
                Collections.singleton(Characteristics.UNORDERED)
        );
    }

    public static <T> Collector<T, ?, List<Entry<Integer, T>>> denseRanking(Comparator<? super T> comparator) {
        return new CollectorImpl<T, List<T>, List<Entry<Integer, T>>>(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> {
                    List<Integer> ranks = new ArrayList<>(list.size());
                    ranks.add(1);

//                    ranks.add(new Pair<>(1, list.get(0)));

                    for (int i = 1; i < list.size(); i++) {
                        T t = list.get(i);
                        int comparison = comparator.compare(t, list.get(i - 1));

                        int rank = comparison == 0 ? ranks.get(i - 1) : i + 1;
                        ranks.add(rank);
                    }

                    List<Entry<Integer, T>> entries = new ArrayList<>(list.size());
                    for (int i = 0; i < list.size(); i++) {
                        entries.add(new SimpleEntry<>(ranks.get(i), list.get(i)));
                    }

                    return entries;
                },
                Collections.singleton(Characteristics.UNORDERED)
        );
    }

    // -------------------------------------------------------------------------------------------------

    private static class Pair<L, R> {
        L left;
        R right;

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public void setLeft(L left) {
            this.left = left;
        }

        public R getRight() {
            return right;
        }

        public void setRight(R right) {
            this.right = right;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.left, this.right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(this.left, pair.left) && Objects.equals(this.right, pair.right);
        }

        @Override
        public String toString() {
            return "Pair(" + this.left + ", " + this.right + ")";
        }
    }

    // -------------------------------------------------------------------------------------------------

    private static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        private CollectorImpl(Supplier<A> supplier,
                              BiConsumer<A, T> accumulator,
                              BinaryOperator<A> combiner,
                              Function<A, R> finisher,
                              Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        @SuppressWarnings("unchecked")
        private CollectorImpl(Supplier<A> supplier,
                              BiConsumer<A, T> accumulator,
                              BinaryOperator<A> combiner,
                              Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, t -> (R) t, characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

}

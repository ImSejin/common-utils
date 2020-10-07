package io.github.imsejin.common.util;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

/**
 * Collection utilities
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Checks whether the collection is null or empty.
     *
     * <pre>{@code
     *     isNullOrEmpty(null)      // true
     *     isNullOrEmpty([])        // true
     *     isNullOrEmpty([5, 6])    // false
     * }</pre>
     *
     * @param collection collection
     * @return whether the collection is null or empty
     */
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> Collection<T> ifNullOrEmpty(Collection<T> collection, Collection<T> defaultCollection) {
        return isNullOrEmpty(collection) ? defaultCollection : collection;
    }

    public static <T> Collection<T> ifNullOrEmpty(Collection<T> collection, @Nonnull Supplier<Collection<T>> supplier) {
        return isNullOrEmpty(collection) ? supplier.get() : collection;
    }

    /**
     * Checks whether the collection exists or not.
     *
     * <pre>{@code
     *     exists(null)      // false
     *     exists([])        // false
     *     exists([5, 6])    // true
     * }</pre>
     *
     * @param collection collection
     * @return whether the collection exists or not
     */
    public static boolean exists(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * Converts collection into map whose key is index and value is list's.
     *
     * <pre>{@code
     *     List<String> list = Arrays.asList("A", "B", "C");
     *     toMap(list); // {0: "A", 1: "B", 2: "C"}
     * }</pre>
     *
     * @param collection collection
     * @param <T>        type of element
     * @return map with index as key and element
     */
    public static <T> Map<Integer, T> toMap(@Nonnull Collection<T> collection) {
        return collection.stream().collect(HashMap<Integer, T>::new,
                (map, streamValue) -> map.put(map.size(), streamValue),
                (map, map2) -> {
                });
    }

    /**
     * Returns a list that contains consecutive {@link List#subList(int, int)}s.
     * Inner lists have the same size, but last is smaller.
     *
     * <pre>{@code
     *     partitionBySize([1, 2, 3, 4, 5], 1); // [[1], [2], [3], [4], [5]]
     *     partitionBySize([1, 2, 3, 4, 5], 3); // [[1, 2, 3], [4, 5]]
     *     partitionBySize([1, 2, 3, 4, 5], 6); // [[1, 2, 3, 4, 5]]
     * }</pre>
     *
     * @param list      origin list
     * @param chunkSize size of inner list
     * @param <T>       any type
     * @return lists partitioned by size
     * @throws IllegalArgumentException if size is non-positive
     */
    public static <T> List<List<T>> partitionBySize(@Nonnull List<T> list, int chunkSize) {
        if (chunkSize < 1) throw new IllegalArgumentException("Size of each list must be greater than or equal to 1");

        /*
        The following code can be replaced with this code.

        for (int i = 0; i < list.size(); i += chunkSize) {
            superList.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
         */

        int originSize = list.size();
        int quotient = Math.floorDiv(originSize, chunkSize);

        List<List<T>> superList = new ArrayList<>();
        for (int i = 0; i < quotient; i++) {
            superList.add(list.subList(i * chunkSize, (i + 1) * chunkSize));
        }

        int remainder = Math.floorMod(originSize, chunkSize);
        if (remainder > 0) superList.add(list.subList(chunkSize * quotient, chunkSize * quotient + remainder));

        return superList;
    }

    /**
     * Returns a list that contains consecutive {@link List#subList(int, int)}s.
     * Inner lists have the same size, but last is smaller or biggest.
     * Outer list has inner lists as many as value of the parameter {@code count}.
     *
     * <pre>{@code
     *     partitionByCount([1, 2, 3, 4, 5], 1); // [[1, 2, 3, 4, 5]]
     *     partitionByCount([1, 2, 3, 4, 5], 3); // [[1, 2], [3, 4], [5]]
     *     partitionByCount([1, 2, 3, 4, 5], 5); // [[1], [2], [3], [4], [5]]
     * }</pre>
     *
     * @param list  origin list
     * @param count size of outer list
     * @param <T>   any type
     * @return lists partitioned by count
     * @throws IllegalArgumentException if count is non-positive or list's size is less than count
     */
    public static <T> List<List<T>> partitionByCount(@Nonnull List<T> list, int count) {
        if (count < 1) throw new IllegalArgumentException("The number of lists must be greater than or equal to 1");
        if (list.size() < count) throw new IllegalArgumentException("Count must be less than list's size");

        int originSize = list.size();
        int quotient = Math.floorDiv(originSize, count);
        int remainder = Math.floorMod(originSize, count);
        int loopCount = remainder > 0 ? count - 1 : count;

        List<List<T>> outer = new ArrayList<>();
        for (int i = 0; i < loopCount; i++) {
            outer.add(list.subList(i * quotient, (i + 1) * quotient));
        }

        if (remainder > 0) outer.add(list.subList(quotient * loopCount, originSize));

        return outer;
    }

}

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
     *
     * <pre>{@code
     *     partition([1, 2, 3, 4, 5], 3); // [[1, 2, 3], [4, 5]]
     *     partition([1, 2, 3, 4, 5], 6); // [[1, 2, 3, 4, 5]]
     * }</pre>
     *
     * @param list origin list
     * @param size partition size
     * @param <T>  generic type
     * @return partitioned lists
     * @throws IllegalArgumentException if size non-positive
     */
    public static <T> List<List<T>> partition(@Nonnull List<T> list, int size) {
        if (size < 1) throw new IllegalArgumentException("Size of each list must be greater than or equal to 1");

        int quotient = Math.floorDiv(list.size(), size);

        List<List<T>> superList = new ArrayList<>();
        for (int i = 0; i < quotient; i++) {
            superList.add(list.subList(i * size, (i + 1) * size));
        }

        int remainder = Math.floorMod(list.size(), size);
        if (remainder > 0) superList.add(list.subList(size * quotient, size * quotient + remainder));

        return superList;
    }

}

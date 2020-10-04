package io.github.imsejin.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
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
    public static <T> Map<Integer, T> toMap(Collection<T> collection) {
        return collection.stream().collect(HashMap<Integer, T>::new,
                (map, streamValue) -> map.put(map.size(), streamValue),
                (map, map2) -> {
                });
    }

}

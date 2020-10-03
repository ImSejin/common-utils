package io.github.imsejin.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection utilities
 */
public final class CollectionUtils {

    private CollectionUtils() {}

    /**
     * Converts collection into map whose key is index and value is list's.
     *
     * <pre>{@code
     *     List<String> list = Arrays.asList("A", "B", "C");
     *     toMap(list); // {0: "A", 1: "B", 2: "C"}
     * }</pre>
     *
     * @param c collection
     * @param <T>  type of element
     * @return map with index as key and element
     */
    public static <T> Map<Integer, T> toMap(Collection<T> c) {
        return c.stream().collect(HashMap<Integer, T>::new,
                (map, streamValue) -> map.put(map.size(), streamValue),
                (map, map2) -> {});
    }

}

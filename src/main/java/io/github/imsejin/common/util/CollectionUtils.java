/*
 * Copyright 2020 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.util;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;
import io.github.imsejin.common.assertion.Asserts;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Collection utilities
 */
public final class CollectionUtils {

    @ExcludeFromGeneratedJacocoReport
    private CollectionUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks whether the collection is null or empty.
     *
     * <pre><code>
     *     isNullOrEmpty(null);   // true
     *     isNullOrEmpty([]);     // true
     *     isNullOrEmpty([5, 6]); // false
     * </code></pre>
     *
     * @param collection collection
     * @return whether the collection is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks whether the map is null or empty.
     *
     * <pre><code>
     *     isNullOrEmpty(null);   // true
     *     isNullOrEmpty({});     // true
     *     isNullOrEmpty({a: 5}); // false
     * </code></pre>
     *
     * @param map map
     * @return whether the map is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <E> Collection<E> ifNullOrEmpty(Collection<E> collection, Collection<E> defaultList) {
        return isNullOrEmpty(collection) ? defaultList : collection;
    }

    public static <E> Collection<E> ifNullOrEmpty(Collection<E> collection, Supplier<Collection<E>> supplier) {
        return isNullOrEmpty(collection) ? supplier.get() : collection;
    }

    public static <E> List<E> ifNullOrEmpty(List<E> list, List<E> defaultList) {
        return isNullOrEmpty(list) ? defaultList : list;
    }

    public static <E> List<E> ifNullOrEmpty(List<E> list, Supplier<List<E>> supplier) {
        return isNullOrEmpty(list) ? supplier.get() : list;
    }

    public static <E> Set<E> ifNullOrEmpty(Set<E> set, Set<E> defaultSet) {
        return isNullOrEmpty(set) ? defaultSet : set;
    }

    public static <E> Set<E> ifNullOrEmpty(Set<E> set, Supplier<Set<E>> supplier) {
        return isNullOrEmpty(set) ? supplier.get() : set;
    }

    public static <K, V> Map<K, V> ifNullOrEmpty(Map<K, V> map, Map<K, V> defaultMap) {
        return isNullOrEmpty(map) ? defaultMap : map;
    }

    public static <K, V> Map<K, V> ifNullOrEmpty(Map<K, V> map, Supplier<Map<K, V>> supplier) {
        return isNullOrEmpty(map) ? supplier.get() : map;
    }

    /**
     * Checks whether the collection exists or not.
     *
     * <pre><code>
     *     exists(null);   // false
     *     exists([]);     // false
     *     exists([5, 6]); // true
     * </code></pre>
     *
     * @param collection collection
     * @return whether the collection exists or not
     */
    public static boolean exists(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * Checks whether the map exists or not.
     *
     * <pre><code>
     *     exists(null);   // false
     *     exists({});     // false
     *     exists({a: 5}); // true
     * </code></pre>
     *
     * @param map map
     * @return whether the map exists or not
     */
    public static boolean exists(Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

    /**
     * Converts collection into map whose key is index and value is collection's.
     *
     * <pre>{@code
     *     List<String> collection = Arrays.asList("A", "B", "C");
     *     toMap(collection); // {0: "A", 1: "B", 2: "C"}
     * }</pre>
     *
     * @param collection collection
     * @param <E>        type of element
     * @return map with index as key and element
     */
    public static <E> Map<Integer, E> toMap(Collection<E> collection) {
        return collection.stream().collect(HashMap::new,
                (map, streamValue) -> map.put(map.size(), streamValue),
                Map::putAll);
    }

    public static long findMax(Collection<Long> collection) {
        return collection.stream().reduce(Long.MIN_VALUE, Math::max);
    }

    public static <E> Optional<E> findElement(Collection<E> collection, Predicate<E> predicate) {
        return collection.stream().filter(predicate).findFirst();
    }

    /**
     * Returns a list that contains consecutive {@link List#subList(int, int)}s.
     * Inner lists have the same size, but last is smaller.
     *
     * <pre><code>
     *     partitionBySize([1, 2, 3, 4, 5], 1); // [[1], [2], [3], [4], [5]]
     *     partitionBySize([1, 2, 3, 4, 5], 3); // [[1, 2, 3], [4, 5]]
     *     partitionBySize([1, 2, 3, 4, 5], 6); // [[1, 2, 3, 4, 5]]
     * </code></pre>
     *
     * @param list      origin list
     * @param chunkSize size of inner list
     * @param <E>       any type
     * @return lists partitioned by size
     * @throws IllegalArgumentException if size is non-positive
     */
    public static <E> List<List<E>> partitionBySize(List<E> list, int chunkSize) {
        Asserts.that(chunkSize)
                .as("Size of each list must be greater than 0")
                .isGreaterThan(0);

        /*
        The following code can be replaced with this code.

        for (int i = 0; i < list.size(); i += chunkSize) {
            superList.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
         */

        int originSize = list.size();
        int quotient = Math.floorDiv(originSize, chunkSize);

        List<List<E>> superList = new ArrayList<>();
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
     * <pre><code>
     *     partitionByCount([1, 2, 3, 4, 5], 1); // [[1, 2, 3, 4, 5]]
     *     partitionByCount([1, 2, 3, 4, 5], 3); // [[1, 2], [3, 4], [5]]
     *     partitionByCount([1, 2, 3, 4, 5], 5); // [[1], [2], [3], [4], [5]]
     * </code></pre>
     *
     * @param list  origin list
     * @param count size of outer list
     * @param <E>   any type
     * @return lists partitioned by count
     * @throws IllegalArgumentException if count is non-positive or list's size is less than count
     */
    public static <E> List<List<E>> partitionByCount(List<E> list, int count) {
        Asserts.that(count)
                .as("The number of lists must be greater than 0")
                .isGreaterThan(0)
                .as("Count must be less than or equal to list's size")
                .isLessThanOrEqualTo(list.size());

        int originSize = list.size();
        int quotient = Math.floorDiv(originSize, count);
        int remainder = Math.floorMod(originSize, count);
        int loopCount = remainder > 0 ? count - 1 : count;

        List<List<E>> outer = new ArrayList<>();
        for (int i = 0; i < loopCount; i++) {
            outer.add(list.subList(i * quotient, (i + 1) * quotient));
        }

        if (remainder > 0) outer.add(list.subList(quotient * loopCount, originSize));

        return outer;
    }

    /**
     * Finds median value in long array.
     *
     * @param numbers long array
     * @return median value
     * @throws ArrayIndexOutOfBoundsException if array is empty
     */
    public static double median(long... numbers) {
        long[] longs = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(longs);

        double median;
        if (MathUtils.isOdd(longs.length)) {
            median = longs[longs.length / 2];
        } else {
            median = (longs[longs.length / 2] + longs[longs.length / 2 - 1]) / 2.0;
        }

        return median;
    }

    /**
     * Finds median value in int array.
     *
     * @param numbers int array
     * @return median value
     * @throws ArrayIndexOutOfBoundsException if array is empty
     */
    public static double median(int... numbers) {
        int[] ints = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(ints);

        double median;
        if (MathUtils.isOdd(ints.length)) {
            median = ints[ints.length / 2];
        } else {
            median = (ints[ints.length / 2] + ints[ints.length / 2 - 1]) / 2.0;
        }

        return median;
    }

}

/*
 * Copyright 2021 Sejin Im
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

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

/**
 * Array utilities
 */
public final class ArrayUtils {

    public static final Map<Class<?>, Object> EMPTY_ARRAY_MAP;

    static {
        Map<Class<?>, Object> cache = new HashMap<>();
        cache.put(boolean.class, new boolean[0]);
        cache.put(byte.class, new byte[0]);
        cache.put(short.class, new short[0]);
        cache.put(char.class, new char[0]);
        cache.put(int.class, new int[0]);
        cache.put(long.class, new long[0]);
        cache.put(float.class, new float[0]);
        cache.put(double.class, new double[0]);
        cache.put(Void.class, new Void[0]);
        cache.put(Boolean.class, new Boolean[0]);
        cache.put(Byte.class, new Byte[0]);
        cache.put(Short.class, new Short[0]);
        cache.put(Character.class, new Character[0]);
        cache.put(Integer.class, new Integer[0]);
        cache.put(Long.class, new Long[0]);
        cache.put(Float.class, new Float[0]);
        cache.put(Double.class, new Double[0]);

        EMPTY_ARRAY_MAP = Collections.unmodifiableMap(cache);
    }

    @ExcludeFromGeneratedJacocoReport
    private ArrayUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    public static boolean isNullOrEmpty(Object array) {
        return array == null || Array.getLength(array) == 0;
    }

    public static boolean exists(Object array) {
        return !isNullOrEmpty(array);
    }

    /**
     * Makes primitive array boxed.
     *
     * @param array primitive array or {@code null} if {@code array} is {@code null}
     * @return boxed array
     */
    @Nullable
    public static Object wrap(@Nullable Object array) {
        if (array == null) return null;

        // When parameter is not array, returns as it is.
        Class<?> componentType = array.getClass().getComponentType();
        if (componentType == null) return array;

        int length = Array.getLength(array);
        Class<?> wrapped = ClassUtils.wrap(componentType);
        Object wrappedArray = Array.newInstance(wrapped, length);

        for (int i = 0; i < length; i++) {
            // If element is primitive, it is already auto-boxed by Array.get(Object, int).
            Object element = Array.get(array, i);

            // Primitive value is not allowed to be null, so the element is considered as Array.
            // When element is array, do again recursively.
            if (element == null || element.getClass().isArray()) {
                element = wrap(element);
            }

            Array.set(wrappedArray, i, element);
        }

        return wrappedArray;
    }

    /**
     * Makes boxed array unboxed.
     *
     * @param array boxed array or {@code null} if {@code array} is {@code null}
     * @return primitive array
     */
    @Nullable
    public static Object unwrap(@Nullable Object array) {
        if (array == null) return null;

        // When parameter is not array, returns as it is.
        Class<?> componentType = array.getClass().getComponentType();
        if (componentType == null) return array;

        int length = Array.getLength(array);
        Class<?> primitive = ClassUtils.unwrap(componentType);
        Object primitiveArray = Array.newInstance(primitive, length);

        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);

            // Primitive value is not allowed to be null, so the element is considered as Array.
            // When element is array, do again recursively.
            if (element == null || element.getClass().isArray()) {
                element = unwrap(element);
            }

            // If component type of array is primitive,
            // the element will be auto-unboxed by Array.set(Object, int, Object).
            Array.set(primitiveArray, i, element);
        }

        return primitiveArray;
    }

    /**
     * Returns stringified object regardless of whether it is null or array.
     *
     * <pre><code>
     *     toString(null);                                                  // "null"
     *     toString(new Object());                                          // "java.lang.Object@28c97a5"
     *     toString(new int[] {0, 1, 2});                                   // "[0, 1, 2]"
     *     toString(Arrays.asList("io", "github"));                         // "[io, github]"
     *     toString(Arrays.asList(new String[] {"a"}, new String[] {"b"})); // "[[a], [b]]"
     *     toString(Collections.singletonMap('a', new int[][] {{1}, {2}})); // "{a=[[1], [2]]}"
     * </code></pre>
     *
     * @param array maybe array
     * @return stringified object
     */
    public static String toString(@Nullable Object array) {
        if (array == null) return "null";

        Class<?> clazz = array.getClass();
        if (clazz.isArray()) {
            // One dimensional object array or multi-dimensional array.
            if (array instanceof Object[]) return Arrays.deepToString((Object[]) array);

            // One dimensional primitive array.
            Class<?> componentType = clazz.getComponentType();
            if (componentType == boolean.class) return Arrays.toString((boolean[]) array);
            if (componentType == byte.class) return Arrays.toString((byte[]) array);
            if (componentType == short.class) return Arrays.toString((short[]) array);
            if (componentType == char.class) return Arrays.toString((char[]) array);
            if (componentType == int.class) return Arrays.toString((int[]) array);
            if (componentType == long.class) return Arrays.toString((long[]) array);
            if (componentType == float.class) return Arrays.toString((float[]) array);
            if (componentType == double.class) return Arrays.toString((double[]) array);

        } else if (Iterable.class.isAssignableFrom(clazz)) {
            Iterator<?> iterator = ((Iterable<?>) array).iterator();

            StringBuilder sb = new StringBuilder("[");
            if (iterator.hasNext()) {
                sb.append(toString(iterator.next()));

                while (iterator.hasNext()) {
                    sb.append(", ").append(toString(iterator.next()));
                }
            }
            sb.append("]");

            return sb.toString();

        } else if (Map.class.isAssignableFrom(clazz)) {
            Iterator<? extends Entry<?, ?>> iterator = ((Map<?, ?>) array).entrySet().iterator();

            StringBuilder sb = new StringBuilder("{");
            if (iterator.hasNext()) {
                Entry<?, ?> e0 = iterator.next();
                sb.append(toString(e0.getKey())).append('=').append(toString(e0.getValue()));

                while (iterator.hasNext()) {
                    Entry<?, ?> e1 = iterator.next();
                    sb.append(", ").append(toString(e1.getKey())).append('=').append(toString(e1.getValue()));
                }
            }
            sb.append("}");

            return sb.toString();
        }

        // Others.
        return array.toString();
    }

    /**
     * Prepends elements to array.
     *
     * <pre><code>
     *     prepend(new Character[]{'a', 'b'}, "c", "d"); // ["c", "d", 'a', 'b']
     *     prepend(new int[]{3, 4, 5, 6}, 0, 1, 2);      // [0, 1, 2, 3, 4, 5, 6]
     * </code></pre>
     *
     * @param src      source array
     * @param elements elements to be prepended
     * @return prepended array
     */
    public static Object[] prepend(Object[] src, @Nullable Object... elements) {
        if (isNullOrEmpty(elements)) return src;

        Object[] array = new Object[elements.length + src.length];
        System.arraycopy(elements, 0, array, 0, elements.length);
        if (exists(src)) System.arraycopy(src, 0, array, elements.length, src.length);

        return array;
    }

    /**
     * Appends elements to array.
     *
     * <pre><code>
     *     append(new Character[]{'a', 'b'}, "c", "d"); // ['a', 'b', "c", "d"]
     *     append(new int[]{3, 4, 5, 6}, 0, 1, 2);      // [3, 4, 5, 6, 0, 1, 2]
     * </code></pre>
     *
     * @param src      source array
     * @param elements elements to be appended
     * @return appended array
     */
    public static Object[] append(Object[] src, @Nullable Object... elements) {
        if (isNullOrEmpty(elements)) return src;

        Object[] array = new Object[src.length + elements.length];
        if (exists(src)) System.arraycopy(src, 0, array, 0, src.length);
        System.arraycopy(elements, 0, array, src.length, elements.length);

        return array;
    }

    /**
     * Returns array type with the given dimension.
     *
     * @param type      type
     * @param dimension dimension
     * @return type of array that has the given dimension
     */
    public static Class<?> resolveArrayType(Class<?> type, int dimension) {
        for (int i = 0; i < dimension; i++) {
            type = Array.newInstance(type, 0).getClass();
        }

        return type;
    }

    /**
     * Returns the actual component type of array.
     *
     * <pre>
     *     resolveActualComponentType(char.class);       // char.class
     *     resolveActualComponentType(int[].class);      // int.class
     *     resolveActualComponentType(Double[][].class); // Double.class
     * </pre>
     *
     * @param type array type
     * @return actual component type
     */
    public static Class<?> resolveActualComponentType(Class<?> type) {
        while (type.isArray()) {
            type = type.getComponentType();
        }

        return type;
    }

    /**
     * Returns dimension of the given array type.
     *
     * @param type array type
     * @return dimension of array
     */
    public static int dimensionOf(Class<?> type) {
        int dimension = 0;
        while (type.isArray()) {
            type = type.getComponentType();
            dimension++;
        }

        return dimension;
    }

}

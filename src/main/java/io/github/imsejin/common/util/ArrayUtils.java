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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array utilities
 */
public final class ArrayUtils {

    private static final Boolean[] EMPTY_BOOLEAN_WRAPPER_ARRAY = new Boolean[0];
    private static final Byte[] EMPTY_BYTE_WRAPPER_ARRAY = new Byte[0];
    private static final Character[] EMPTY_CHARACTER_WRAPPER_ARRAY = new Character[0];
    private static final Double[] EMPTY_DOUBLE_WRAPPER_ARRAY = new Double[0];
    private static final Float[] EMPTY_FLOAT_WRAPPER_ARRAY = new Float[0];
    private static final Integer[] EMPTY_INTEGER_WRAPPER_ARRAY = new Integer[0];
    private static final Long[] EMPTY_LONG_WRAPPER_ARRAY = new Long[0];
    private static final Short[] EMPTY_SHORT_WRAPPER_ARRAY = new Short[0];

    private static final boolean[] EMPTY_BOOLEAN_PRIMITIVE_ARRAY = new boolean[0];
    private static final byte[] EMPTY_BYTE_PRIMITIVE_ARRAY = new byte[0];
    private static final char[] EMPTY_CHARACTER_PRIMITIVE_ARRAY = new char[0];
    private static final double[] EMPTY_DOUBLE_PRIMITIVE_ARRAY = new double[0];
    private static final float[] EMPTY_FLOAT_PRIMITIVE_ARRAY = new float[0];
    private static final int[] EMPTY_INTEGER_PRIMITIVE_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_PRIMITIVE_ARRAY = new long[0];
    private static final short[] EMPTY_SHORT_PRIMITIVE_ARRAY = new short[0];

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
     * @param array primitive array
     * @return boxed array
     */
    public static Object wrap(@Nullable Object array) {
        if (array == null) return null;

        int length = Array.getLength(array);
        Class<?> wrapped = ClassUtils.wrap(array.getClass().getComponentType());

        Object instance = Array.newInstance(wrapped, length);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(array, i);
            Array.set(instance, i, value);
        }

        return instance;
    }

    /**
     * Makes boxed array unboxed.
     *
     * @param array boxed array
     * @return primitive array
     */
    public static Object unwrap(@Nullable Object array) {
        if (array == null) return null;

        int length = Array.getLength(array);
        Class<?> primitive = ClassUtils.unwrap(array.getClass().getComponentType());

        Object instance = Array.newInstance(primitive, length);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(array, i);
            Array.set(instance, i, value);
        }

        return instance;
    }

    /**
     * Returns stringified object regardless of whether it is null or array.
     *
     * <pre><code>
     *     toString(null);                          // "null"
     *     toString(new int[] {0, 1, 2});           // "[0, 1, 2]"
     *     toString(new Object());                  // "java.lang.Object@28c97a5"
     *     toString(Arrays.asList("io", "github")); // "[io, github]"
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
    public static Object[] prepend(@Nonnull Object[] src, Object... elements) {
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
    public static Object[] append(@Nonnull Object[] src, Object... elements) {
        if (isNullOrEmpty(elements)) return src;

        Object[] array = new Object[src.length + elements.length];
        if (exists(src)) System.arraycopy(src, 0, array, 0, src.length);
        System.arraycopy(elements, 0, array, src.length, elements.length);

        return array;
    }

}

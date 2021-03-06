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

    public static boolean isNullOrEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean exists(boolean[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(byte[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(char[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(double[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(float[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(int[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(long[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean exists(short[] array) {
        return !isNullOrEmpty(array);
    }

    public static <T> boolean exists(T[] array) {
        return !isNullOrEmpty(array);
    }

    @Nullable
    public static Boolean[] toWrapper(boolean[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_BOOLEAN_WRAPPER_ARRAY;

        Boolean[] booleans = new Boolean[array.length];
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = Boolean.valueOf(array[i]);
        }

        return booleans;
    }

    @Nullable
    public static Byte[] toWrapper(byte[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_BYTE_WRAPPER_ARRAY;

        Byte[] bytes = new Byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = Byte.valueOf(array[i]);
        }

        return bytes;
    }

    @Nullable
    public static Character[] toWrapper(char[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_CHARACTER_WRAPPER_ARRAY;

        Character[] characters = new Character[array.length];
        for (int i = 0; i < characters.length; i++) {
            characters[i] = Character.valueOf(array[i]);
        }

        return characters;
    }

    @Nullable
    public static Double[] toWrapper(double[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_DOUBLE_WRAPPER_ARRAY;

        Double[] doubles = new Double[array.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = Double.valueOf(array[i]);
        }

        return doubles;
    }

    @Nullable
    public static Float[] toWrapper(float[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_FLOAT_WRAPPER_ARRAY;

        Float[] floats = new Float[array.length];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = Float.valueOf(array[i]);
        }

        return floats;
    }

    @Nullable
    public static Integer[] toWrapper(int[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_INTEGER_WRAPPER_ARRAY;

        Integer[] integers = new Integer[array.length];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = Integer.valueOf(array[i]);
        }

        return integers;
    }

    @Nullable
    public static Long[] toWrapper(long[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_LONG_WRAPPER_ARRAY;

        Long[] longs = new Long[array.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.valueOf(array[i]);
        }

        return longs;
    }

    @Nullable
    public static Short[] toWrapper(short[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_SHORT_WRAPPER_ARRAY;

        Short[] shorts = new Short[array.length];
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = Short.valueOf(array[i]);
        }

        return shorts;
    }

    @Nullable
    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_BOOLEAN_PRIMITIVE_ARRAY;

        boolean[] booleans = new boolean[array.length];
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = array[i].booleanValue();
        }

        return booleans;
    }

    @Nullable
    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_BYTE_PRIMITIVE_ARRAY;

        byte[] bytes = new byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = array[i].byteValue();
        }

        return bytes;
    }

    @Nullable
    public static char[] toPrimitive(Character[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_CHARACTER_PRIMITIVE_ARRAY;

        char[] chars = new char[array.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = array[i].charValue();
        }

        return chars;
    }

    @Nullable
    public static double[] toPrimitive(Double[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_DOUBLE_PRIMITIVE_ARRAY;

        double[] doubles = new double[array.length];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = array[i].doubleValue();
        }

        return doubles;
    }

    @Nullable
    public static float[] toPrimitive(Float[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_FLOAT_PRIMITIVE_ARRAY;

        float[] floats = new float[array.length];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = array[i].floatValue();
        }

        return floats;
    }

    @Nullable
    public static int[] toPrimitive(Integer[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_INTEGER_PRIMITIVE_ARRAY;

        int[] ints = new int[array.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = array[i].intValue();
        }

        return ints;
    }

    @Nullable
    public static long[] toPrimitive(Long[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_LONG_PRIMITIVE_ARRAY;

        long[] longs = new long[array.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = array[i].longValue();
        }

        return longs;
    }

    @Nullable
    public static short[] toPrimitive(Short[] array) {
        if (array == null) return null;
        if (array.length == 0) return EMPTY_SHORT_PRIMITIVE_ARRAY;

        short[] shorts = new short[array.length];
        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = array[i].shortValue();
        }

        return shorts;
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
        if (array.getClass().isArray()) return Arrays.toString((Object[]) array);
        return array.toString();
    }

}

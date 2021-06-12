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

package io.github.imsejin.common.tool;

import io.github.imsejin.common.annotation.ExcludeFromGeneratedJacocoReport;

import java.time.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class TypeClassifier {

    @ExcludeFromGeneratedJacocoReport
    private TypeClassifier() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks if type is temporal.
     *
     * @param type class
     * @return whether type is {@link LocalDate}, {@link LocalTime}, {@link LocalDateTime}
     * {@link ZonedDateTime}, {@link OffsetDateTime} or {@link OffsetTime}
     */
    public static boolean isTemporal(Class<?> type) {
        return contains(type, Types.DATETIME);
    }

    /**
     * Checks if type is numeric and primitive.
     *
     * @param type class
     * @return whether type is {@code byte}, {@code short}, {@code int}, {@code long}, {@code float} or {@code double}
     */
    public static boolean isNumericPrimitive(Class<?> type) {
        return contains(type, Types.PRIMITIVE_NUMBER);
    }

    /**
     * Checks if type is numeric and wrapper.
     *
     * @param type class
     * @return whether type is {@link Byte}, {@link Short}, {@link Integer}, {@link Long}, {@link Float} or {@link Double}
     */
    public static boolean isNumericWrapper(Class<?> type) {
        return contains(type, Types.WRAPPER_NUMBER);
    }

    /**
     * Checks if type is numeric.
     *
     * @param type class
     * @return whether type is {@code byte}, {@code short}, {@code int}, {@code long}, {@code float}, {@code double},
     * {@link Byte}, {@link Short}, {@link Integer}, {@link Long}, {@link Float} or {@link Double}
     */
    public static boolean isNumeric(Class<?> type) {
        return contains(type, Types.NUMBER);
    }

    /**
     * Checks if type is string.
     *
     * @param type class
     * @return whether type is {@link String}
     */
    public static boolean isString(Class<?> type) {
        return type == String.class;
    }

    /**
     * Checks if type is primitive.
     *
     * @param type class
     * @return whether type is {@code byte}, {@code short}, {@code int}, {@code long},
     * {@code float}, {@code double}, {@code char} or {@code boolean}
     */
    public static boolean isPrimitive(Class<?> type) {
        return contains(type, Types.PRIMITIVE);
    }

    /**
     * Checks if type is wrapper class.
     *
     * @param type class
     * @return whether type is {@link Byte}, {@link Short}, {@link Integer}, {@link Long},
     * {@link Float}, {@link Double}, {@link Character} or {@link Boolean}
     */
    public static boolean isWrapper(Class<?> type) {
        return contains(type, Types.WRAPPER);
    }

    public static Class<?> toWrapper(Class<?> primitiveType) {
        if (!primitiveType.isPrimitive()) return primitiveType;

        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == short.class) return Short.class;
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == double.class) return Double.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == void.class) return Void.class;

        return primitiveType;
    }

    public static Class<?> toPrimitive(Class<?> wrapperType) {
        if (wrapperType.isPrimitive()) return wrapperType;

        if (wrapperType == Byte.class) return byte.class;
        if (wrapperType == Short.class) return short.class;
        if (wrapperType == Integer.class) return int.class;
        if (wrapperType == Long.class) return long.class;
        if (wrapperType == Float.class) return float.class;
        if (wrapperType == Double.class) return double.class;
        if (wrapperType == Character.class) return char.class;
        if (wrapperType == Boolean.class) return boolean.class;
        if (wrapperType == Void.class) return void.class;

        return wrapperType;
    }

    private static boolean contains(Class<?> type, Types types) {
        if (type == null) return false;
        return types.classes.contains(type);
    }

    public enum Types {
        /**
         * Primitive types.
         */
        PRIMITIVE(byte.class, short.class, int.class, long.class,
                float.class, double.class, char.class, boolean.class, void.class),

        /**
         * Wrapper types.
         */
        WRAPPER(Byte.class, Short.class, Integer.class, Long.class,
                Float.class, Double.class, Character.class, Boolean.class, Void.class),

        /**
         * Numeric primitive types.
         */
        PRIMITIVE_NUMBER(byte.class, short.class, int.class, long.class, float.class, double.class),

        /**
         * Numeric wrapper types.
         */
        WRAPPER_NUMBER(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class),

        /**
         * Numeric types.
         */
        NUMBER(Stream.of(PRIMITIVE_NUMBER.classes, WRAPPER_NUMBER.classes)
                .flatMap(Set::stream).toArray(Class[]::new)),

        /**
         * Datetime types.
         */
        DATETIME(LocalTime.class, LocalDate.class, LocalDateTime.class,
                ZonedDateTime.class, OffsetDateTime.class, OffsetTime.class);

        private final Set<Class<?>> classes;

        Types(Class<?>... classes) {
            this.classes = Collections.unmodifiableSet(Stream.of(classes).collect(toSet()));
        }

        @ExcludeFromGeneratedJacocoReport
        public Set<Class<?>> getClasses() {
            return this.classes;
        }
    }

}

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
import io.github.imsejin.common.model.graph.DirectedGraph;
import io.github.imsejin.common.model.graph.Graph;

import javax.annotation.Nullable;
import java.lang.reflect.*;
import java.util.*;

/**
 * Class utilities
 */
public final class ClassUtils {

    @ExcludeFromGeneratedJacocoReport
    private ClassUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks if this class is enum or one of its constants.
     * <p>
     * A class that extends {@link Enum} and its constants are changed internally since Java 9.
     *
     * <pre>
     * 1. A class that extends {@link Enum}: {@link java.util.concurrent.TimeUnit}
     * +---------+--------+------------------+----------------+----------+-------+
     * | Java \  | isEnum | isAnonymousClass | getSuperclass  | abstract | final |
     * +---------+--------+------------------+----------------+----------+-------+
     * | until 8 | true   | false            | java.lang.Enum | true     | false |
     * +---------+--------+------------------+----------------+----------+-------+
     * | since 9 | true   | false            | java.lang.Enum | false    | true  |
     * +---------+--------+------------------+----------------+----------+-------+
     *
     * 2. A class that has no body, declared in enum class body as a constant: {@link java.time.Month#JANUARY}
     * +---------+--------+------------------+----------------+----------+-------+
     * | Java \  | isEnum | isAnonymousClass | getSuperclass  | abstract | final |
     * +---------+--------+------------------+----------------+----------+-------+
     * | until 8 | true   | false            | java.lang.Enum | false    | true  |
     * +---------+--------+------------------+----------------+----------+-------+
     * | since 9 | true   | false            | java.lang.Enum | false    | true  |
     * +---------+--------+------------------+----------------+----------+-------+
     *
     * 3. A class that has own body, declared in enum class body as a constant: {@link java.util.concurrent.TimeUnit#HOURS}
     * +---------+--------+------------------+-------------------------------+----------+-------+
     * | Java \  | isEnum | isAnonymousClass | getSuperclass                 | abstract | final |
     * +---------+--------+------------------+-------------------------------+----------+-------+
     * | until 8 | false  | true             | java.util.concurrent.TimeUnit | false    | false |
     * +---------+--------+------------------+-------------------------------+----------+-------+
     * | since 9 | true   | false            | java.lang.Enum                | false    | true  |
     * +---------+--------+------------------+-------------------------------+----------+-------+
     * </pre>
     *
     * @param clazz class
     * @return whether this class is enum or its one of a constant.
     * @see Class#isEnum()
     * @see Enum#getDeclaringClass()
     */
    public static boolean isEnumOrEnumConstant(@Nullable Class<?> clazz) {
        return clazz != null && Enum.class.isAssignableFrom(clazz);
    }

    /**
     * Checks if this class is abstract.
     *
     * @param clazz class
     * @return whether this class is abstract
     */
    public static boolean isAbstractClass(@Nullable Class<?> clazz) {
        if (clazz == null) return false;

        // Keyword 'abstract' is cannot be compatible with 'final' except native objects.
        final int modifiers = clazz.getModifiers();
        return Modifier.isAbstract(modifiers) && !Modifier.isFinal(modifiers) &&
                !clazz.isArray() && !clazz.isPrimitive() && !clazz.isInterface() && !isEnumOrEnumConstant(clazz);
    }

    public static boolean isSuperclass(Class<?> superclass, @Nullable Class<?> subclass) {
        if (subclass == null || superclass == subclass) return false;

        for (Class<?> c = subclass.getSuperclass(); c != null; c = c.getSuperclass()) {
            if (c == superclass) return true;
        }

        return false;
    }

    /**
     * Checks if type is wrapper class.
     *
     * <p> If type is {@link Byte}, {@link Short}, {@link Integer},
     * {@link Long}, {@link Float}, {@link Double}, {@link Boolean},
     * {@link Character} or {@link Void}, returns {@code true},
     * or else returns {@code false}.
     *
     * @param type class
     * @return whether type is wrapper class
     */
    public static boolean isWrapper(@Nullable Class<?> type) {
        return isNumericWrapper(type) || type == Boolean.class ||
                type == Character.class || type == Void.class;
    }

    public static boolean isNumeric(@Nullable Class<?> type) {
        return isNumericPrimitive(type) || isNumericWrapper(type);
    }

    /**
     * Checks if type is numeric and primitive class.
     *
     * <p> If type is {@code byte}, {@code short}, {@code int},
     * {@code long}, {@code float} or {@code double}, returns {@code true},
     * or else returns {@code false}.
     *
     * @param type class
     * @return whether type is numeric and primitive
     */
    public static boolean isNumericPrimitive(@Nullable Class<?> type) {
        return type == byte.class || type == short.class || type == int.class ||
                type == long.class || type == float.class || type == double.class;
    }

    /**
     * Checks if type is numeric and wrapper class.
     *
     * <p> If type is {@link Byte}, {@link Short}, {@link Integer},
     * {@link Long}, {@link Float} or {@link Double}, returns {@code true},
     * or else returns {@code false}.
     *
     * @param type class
     * @return whether type is numeric and wrapper class
     */
    public static boolean isNumericWrapper(@Nullable Class<?> type) {
        return type == Byte.class || type == Short.class || type == Integer.class ||
                type == Long.class || type == Float.class || type == Double.class;
    }

    /**
     * Gets initial value of the type.
     *
     * @param type type of the object
     * @return initial value of the type
     */
    @Nullable
    public static Object initialValueOf(@Nullable Class<?> type) {
        // Value of primitive type cannot be null.
        if (isNumericPrimitive(type)) return 0;
        if (type == char.class) return '\u0000';
        if (type == boolean.class) return false;

        // The others are nullable.
        return null;
    }

    /**
     * Makes the primitive type boxed.
     *
     * <p> If the type is not primitive, returns as it is.
     *
     * @param type primitive type
     * @param <T>  type
     * @return wrapper class
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(@Nullable Class<T> type) {
        if (type == null) return null;

        Class<?> clazz = type;
        if (clazz == void.class) clazz = Void.class;
        if (clazz == boolean.class) clazz = Boolean.class;
        if (clazz == byte.class) clazz = Byte.class;
        if (clazz == short.class) clazz = Short.class;
        if (clazz == char.class) clazz = Character.class;
        if (clazz == int.class) clazz = Integer.class;
        if (clazz == long.class) clazz = Long.class;
        if (clazz == float.class) clazz = Float.class;
        if (clazz == double.class) clazz = Double.class;

        if (clazz.isArray()) {
            Class<?> componentType = ArrayUtils.resolveActualComponentType(clazz);
            if (!componentType.isPrimitive()) return type;

            int dimension = ArrayUtils.dimensionOf(clazz);
            return (Class<T>) ArrayUtils.resolveArrayType(wrap(componentType), dimension);
        }

        return (Class<T>) clazz;
    }

    /**
     * Makes the wrapper class unboxed.
     *
     * <p> If the type is not wrapper class, returns as it is.
     *
     * @param type wrapper class
     * @param <T>  type
     * @return primitive type
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(@Nullable Class<T> type) {
        if (type == null) return null;

        Class<?> clazz = type;
        if (clazz == Void.class) clazz = void.class;
        if (clazz == Boolean.class) clazz = boolean.class;
        if (clazz == Byte.class) clazz = byte.class;
        if (clazz == Short.class) clazz = short.class;
        if (clazz == Character.class) clazz = char.class;
        if (clazz == Integer.class) clazz = int.class;
        if (clazz == Long.class) clazz = long.class;
        if (clazz == Float.class) clazz = float.class;
        if (clazz == Double.class) clazz = double.class;

        if (clazz.isArray()) {
            Class<?> componentType = ArrayUtils.resolveActualComponentType(clazz);
            if (componentType.isPrimitive()) return type;

            int dimension = ArrayUtils.dimensionOf(clazz);
            return (Class<T>) ArrayUtils.resolveArrayType(unwrap(componentType), dimension);
        }

        return (Class<T>) clazz;
    }

    /**
     * Returns all the types extended or implemented by this class as a set.
     *
     * <p> There are 3 cases of return value.
     * <dl>
     *     <dt><b>case 1: {@code null}</b></dt>
     *     <dd>Empty set.</dd>
     *
     *     <dt><b>case 2: {@code java.lang.Object}</b></dt>
     *     <dd>Singleton set that contains that.</dd>
     *
     *     <dt><b>case 3: other classes</b></dt>
     *     <dd>Set that contains own class and all the types extended or implemented by that.</dd>
     * </dl>
     *
     * @param clazz endpoint class
     * @return all the types extended or implemented by this class
     * @see <a href="https://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively">
     * Find all classes and interfaces a class extends or implements recursively</a>
     */
    public static Set<Class<?>> getAllExtendedOrImplementedTypesAsSet(@Nullable Class<?> clazz) {
        if (clazz == null) return Collections.emptySet();
        List<Class<?>> classes = new ArrayList<>();

        do {
            classes.add(clazz);

            // First, adds all the interfaces implemented by this class.
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                classes.addAll(Arrays.asList(interfaces));

                for (Class<?> it : interfaces) {
                    classes.addAll(getAllExtendedOrImplementedTypesAsSet(it));
                }
            }

            // Adds the super class.
            Class<?> superclass = clazz.getSuperclass();

            // All interfaces don't have java.lang.Object as superclass.
            // They return null, so breaks the recursive cycle and returns.
            if (superclass == null) break;

            // Now inspects the superclass.
            clazz = superclass;
        } while (clazz != Object.class);

        return new LinkedHashSet<>(classes);
    }

    /**
     * Returns all the types extended or implemented by this class as a graph.
     *
     * <p> There are 3 cases of return value.
     * <dl>
     *     <dt><b>case 1: {@code null}</b></dt>
     *     <dd>Empty graph.</dd>
     *
     *     <dt><b>case 2: {@code java.lang.Object}</b></dt>
     *     <dd>Graph that has one vertex.</dd>
     *
     *     <dt><b>case 3: other classes</b></dt>
     *     <dd>Set that contains own class and all the types extended or implemented by that.</dd>
     * </dl>
     *
     * @param clazz endpoint class
     * @return all the types extended or implemented by this class
     * @see <a href="https://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively">
     * Find all classes and interfaces a class extends or implements recursively</a>
     */
    public static Graph<Class<?>> getAllExtendedOrImplementedTypesAsGraph(@Nullable Class<?> clazz) {
        if (clazz == null) return new DirectedGraph<>();
        Graph<Class<?>> graph = new DirectedGraph<>();

        do {
            graph.addVertex(clazz);

            // First, adds all the interfaces implemented by this class.
            for (Class<?> it : clazz.getInterfaces()) {
                graph.addVertex(it);
                graph.addEdge(clazz, it);
                graph.addAll(getAllExtendedOrImplementedTypesAsGraph(it));
            }

            // Adds the super class.
            Class<?> superclass = clazz.getSuperclass();

            // All interfaces don't have java.lang.Object as superclass.
            // They return null, so breaks the recursive cycle and returns.
            if (superclass == null) break;

            if (superclass != Object.class) {
                graph.addVertex(superclass);
                graph.addEdge(clazz, superclass);
            }

            // Now inspects the superclass.
            clazz = superclass;
        } while (clazz != Object.class);

        return graph;
    }

    /**
     * Resolves the actual types.
     *
     * <p> There are some cases of return value by the given parameter.
     * <dl>
     *     <dt><b>case {@code null}:</b></dt>
     *     <dd>[]</dd>
     *
     *     <dt><b>case Concrete type: {@code String}</b></dt>
     *     <dd>[{@code String}]</dd>
     *
     *     <dt><b>case Raw type: {@code List}</b></dt>
     *     <dd>[{@code List}]</dd>
     *
     *     <dt><b>case Type variable: {@code T}</b></dt>
     *     <dd>[]</dd>
     *
     *     <dt><b>case Type variable array: {@code T[]}</b></dt>
     *     <dd>[{@code Object[]}]</dd>
     *
     *     <dt><b>case Generic unknown: {@code List<?>}</b></dt>
     *     <dd>[{@code Object}]</dd>
     *
     *     <dt><b>case Concrete type in generic: {@code Map<String, Integer>}</b></dt>
     *     <dd>[{@code String}, {@code Integer}]</dd>
     *
     *     <dt><b>case Upper bound type in generic: {@code Class<? extends Number>}</b></dt>
     *     <dd>[{@code Number}]</dd>
     *
     *     <dt><b>case Lower bound type in generic: {@code Class<? super Number>}</b></dt>
     *     <dd>[{@code Number}]</dd>
     * </dl>
     *
     * @param type resolvable type
     * @return actual type
     */
    public static List<Class<?>> resolveActualTypes(@Nullable Type type) {
        // When type is concrete type: java.lang.String
        if (type instanceof Class<?>) return Collections.singletonList((Class<?>) type);

        // When type is wildcard type:
        // java.util.List<? super java.lang.String>
        // java.util.List<? extends java.lang.String>
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type t = ArrayUtils.exists(lowerBounds) ? lowerBounds[0] : wildcardType.getUpperBounds()[0];
            if (t instanceof Class<?>) return Collections.singletonList((Class<?>) t);

            // GenericArrayType: T[]
            if (t instanceof GenericArrayType) return Collections.singletonList(Object[].class);

            // TypeVariable: T
            return Collections.emptyList();
        }

        // GenericArrayType: T[]
        if (type instanceof GenericArrayType) return Collections.singletonList(Object[].class);

        // null, TypeVariable: T
        if (!(type instanceof ParameterizedType)) return Collections.emptyList();

        List<Class<?>> types = new ArrayList<>();
        ParameterizedType paramType = (ParameterizedType) type;
        for (Type t : paramType.getActualTypeArguments()) {
            List<Class<?>> list = resolveActualTypes(t);
            if (!list.isEmpty()) types.addAll(list);
        }

        return types;
    }

}

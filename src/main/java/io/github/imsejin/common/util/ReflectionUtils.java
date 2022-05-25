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
import io.github.imsejin.common.assertion.Asserts;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Reflection utilities
 */
public final class ReflectionUtils {

    @ExcludeFromGeneratedJacocoReport
    private ReflectionUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Gets fields of the type including its inherited fields.
     *
     * <p> Excludes static fields and synthetic fields.
     *
     * @param type type of the object
     * @return inherited and own fields (non-static)
     * @see Class#getDeclaredFields()
     * @see Class#getSuperclass()
     */
    public static List<Field> getInheritedFields(Class<?> type) {
        Asserts.that(type).isNotNull();

        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz = type; clazz != null; clazz = clazz.getSuperclass()) {
            fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
        }

        // Removes static fields; Static member is not a target of extension.
        Predicate<Field> filter = it -> Modifier.isStatic(it.getModifiers());

        // Removes the synthetic fields; Only includes user-defined fields.
        // 1. Reference in non-static nested class to its enclosing class. (this$0, this$1, ...)
        // 2. Field(groovy.lang.MetaClass metaClass) for meta-programming on groovy class.
        filter = filter.or(Field::isSynthetic);

        fields.removeIf(filter);

        return fields;
    }

    /**
     * Returns value of the field.
     *
     * <p> Be aware of that when you invoke this method reusing
     * a {@link Field} instance that is not accessible on multi-threaded environment,
     * sometimes you fail to get value of the {@code field} and get an exception.
     * If you don't have access to that {@code field}, this enables you to do temporarily.
     * After all the work is done, this turns back its accessibility as it was.
     * On multi-threaded environment, this makes you encounter an exception.
     *
     * <p> It is recommended on the situation, that you should set {@code true} to the
     * accessibility of {@link Field} instance, before invoking this method.
     *
     * @param instance instance if field is static, null
     * @param field    field
     * @return field value
     * @throws RuntimeException if failed to get value from the field
     */
    @Nullable
    public static Object getFieldValue(@Nullable Object instance, Field field) {
        Asserts.that(field).isNotNull();
        if (!Modifier.isStatic(field.getModifiers())) Asserts.that(instance).isNotNull();

        // Enables to have access to the field even private field.
        boolean accessible = field.isAccessible();
        if (!accessible) field.setAccessible(true);

        try {
            // Returns value of the field.
            return field.get(instance);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to get value from the field(%s) of the class(%s)",
                    field.getName(), field.getDeclaringClass().getName());
            throw new RuntimeException(message, e);
        } finally {
            // Turns back the accessibility of the field as it was.
            if (!accessible) field.setAccessible(false);
        }
    }

    /**
     * Sets up value into the field.
     *
     * <p> Be aware of that when you invoke this method reusing
     * a {@link Field} instance that is not accessible on multi-threaded environment,
     * sometimes you fail to set value of the {@code field} and get an exception.
     * If you don't have access to that {@code field}, this enables you to do temporarily.
     * After all the work is done, this turns back its accessibility as it was.
     * On multi-threaded environment, this makes you encounter an exception.
     *
     * <p> It is recommended on the situation, that you should set {@code true} to the
     * accessibility of {@link Field} instance, before invoking this method.
     *
     * @param instance instance if method is static, null
     * @param field    field
     * @param value    value to be set into field
     * @throws RuntimeException if failed to set {@code value} into the {@code field}
     */
    public static void setFieldValue(@Nullable Object instance, Field field, Object value) {
        Asserts.that(field).isNotNull();
        if (!Modifier.isStatic(field.getModifiers())) Asserts.that(instance).isNotNull();
        if (field.getType().isPrimitive()) Asserts.that(value)
                .as("Value is not allowed to set null to primitive field: {0} <= null", field.getType())
                .isNotNull();

        // Enables to have access to the field even private field.
        boolean accessible = field.isAccessible();
        if (!accessible) field.setAccessible(true);

        try {
            // Sets value to the field.
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to set value into the field(%s) of the class(%s)",
                    field.getName(), field.getDeclaringClass().getName());
            throw new RuntimeException(message, e);
        } finally {
            // Turns back the accessibility of the field as it was.
            if (!accessible) field.setAccessible(false);
        }
    }

    /**
     * Returns the specific constructor declared by given type.
     *
     * @param type       class
     * @param paramTypes parameter types of constructor
     * @param <T>        declaring class of constructor
     * @return constructor
     */
    public static <T> Constructor<T> getDeclaredConstructor(Class<T> type, @Nullable Class<?>... paramTypes) {
        Asserts.that(type).isNotNull();
        if (paramTypes != null) Asserts.that(paramTypes).doesNotContainNull();

        try {
            // Gets constructor with the specific parameter types.
            return type.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            String message = String.format("Not found constructor: %s(%s)",
                    type, Arrays.toString(paramTypes).replaceAll("[\\[\\]]", ""));
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Creates an instance of type using default constructor.
     *
     * @param type class
     * @param <T>  type of instance
     * @return instance of type
     * @throws RuntimeException if {@code type} doesn't have default constructor
     * @throws RuntimeException if failed to instantiate
     */
    public static <T> T instantiate(Class<T> type) {
        Constructor<T> constructor = getDeclaredConstructor(type);
        return instantiate(constructor);
    }

    /**
     * Creates an instance of type using the constructor.
     *
     * <p> Be aware of that when you invoke this method reusing
     * a {@link Constructor} instance that is not accessible on multi-threaded
     * environment, sometimes you fail to instantiate and get an exception.
     * If you don't have access to that {@code constructor}, this enables you
     * to do temporarily. After all the work is done, this turns back its
     * accessibility as it was. On multi-threaded environment, this makes you
     * encounter an exception.
     *
     * <p> It is recommended on the situation, that you should set {@code true} to the
     * accessibility of {@link Constructor} instance, before invoking this method.
     *
     * @param constructor constructor declared in type
     * @param initArgs    initial arguments of constructor
     * @param <T>         type of instance
     * @return instance of type
     * @throws RuntimeException if {@code initArgs} doesn't match with {@code constructor.parameterTypes}
     * @throws RuntimeException if failed to instantiate
     */
    public static <T> T instantiate(Constructor<T> constructor, @Nullable Object... initArgs) {
        Asserts.that(constructor).isNotNull();
        if (initArgs != null) Asserts.that(initArgs).isSameLength(constructor.getParameterTypes());

        boolean accessible = constructor.isAccessible();
        if (!accessible) constructor.setAccessible(true);

        try {
            // Creates an instance of the type.
            return constructor.newInstance(initArgs);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate by constructor: " + constructor, e);
        } finally {
            // Turns back the accessibility of the constructor as it was.
            if (!accessible) constructor.setAccessible(false);
        }
    }

    /**
     * Returns the specific method declared by given type.
     *
     * @param type       class
     * @param name       method name
     * @param paramTypes parameter types of method
     * @return method
     */
    public static Method getDeclaredMethod(Class<?> type, String name, @Nullable Class<?>... paramTypes) {
        Asserts.that(type).isNotNull();
        Asserts.that(name).isNotNull().hasText();
        if (paramTypes != null) Asserts.that(paramTypes).doesNotContainNull();

        try {
            return type.getDeclaredMethod(name, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes the method and returns its result.
     *
     * <p> Be aware of that when you invoke this method reusing
     * a {@link Method} instance that is not accessible on multi-threaded
     * environment, sometimes you fail to invoke the {@code method} and get an exception.
     * If you don't have access to that {@code method}, this enables you to do temporarily.
     * After all the work is done, this turns back its accessibility as it was.
     * On multi-threaded environment, this makes you encounter an exception.
     *
     * <p> It is recommended on the situation, that you should set {@code true} to the
     * accessibility of {@link Method} instance, before invoking this method.
     *
     * @param method   method
     * @param instance instance of method if method is static, null
     * @param args     arguments of method
     * @return result of method
     */
    public static Object invoke(Method method, @Nullable Object instance, Object... args) {
        Asserts.that(method).isNotNull();
        if (!Modifier.isStatic(method.getModifiers())) Asserts.that(instance).isNotNull();
        if (args != null) Asserts.that(args).isSameLength(method.getParameterTypes());

        boolean accessible = method.isAccessible();
        if (!accessible) method.setAccessible(true);

        try {
            return method.invoke(instance, args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        } finally {
            // Turns back the accessibility of the method as it was.
            if (!accessible) method.setAccessible(false);
        }
    }

    /**
     * Runs the executable and returns its result.
     *
     * <p> Be aware of that when you invoke this method reusing
     * a {@link Executable} instance that is not accessible on multi-threaded
     * environment, sometimes you fail to execute the {@code executable} and
     * get an exception. If you don't have access to that {@code executable},
     * this enables you to do temporarily. After all the work is done,
     * this turns back its accessibility as it was. On multi-threaded environment,
     * this makes you encounter an exception.
     *
     * <p> It is recommended on the situation, that you should set {@code true} to the
     * accessibility of {@link Executable} instance, before invoking this method.
     *
     * @param executable constructor or method
     * @param instance   instance if {@code executable} is not instance method, null
     * @param args       arguments of the executable
     * @return result of the executable
     * @see #invoke(Method, Object, Object...)
     * @see #instantiate(Constructor, Object...)
     */
    public static Object execute(Executable executable, @Nullable Object instance, Object... args) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return invoke(method, instance, args);

        } else if (executable instanceof Constructor) {
            Constructor<?> constructor = (Constructor<?>) executable;
            return instantiate(constructor, args);

        } else {
            throw new UnsupportedOperationException("Unsupported implementation of Executable: " + executable);
        }
    }

}

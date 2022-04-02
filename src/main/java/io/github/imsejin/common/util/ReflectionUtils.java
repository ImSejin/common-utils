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
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Reflection utilities
 */
public final class ReflectionUtils {

    private static final Pattern OUTER_CLASS_REF_PATTERN = Pattern.compile("^this\\$[0-9]+$");

    @ExcludeFromGeneratedJacocoReport
    private ReflectionUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Gets fields of the type including its inherited fields.
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

        // Removes static fields.
        Predicate<Field> filter = it -> Modifier.isStatic(it.getModifiers());

        // Removes the fields for reference to outer class when a type is non-static.
        // e.g. this$0, this$1, ...
        filter = filter.or(it -> Modifier.isFinal(it.getModifiers()) &&
                OUTER_CLASS_REF_PATTERN.matcher(it.getName()).matches());

        // Removes internal field for meta-programming in groovy class.
        filter = filter.or(it -> Modifier.isTransient(it.getModifiers()) &&
                it.getType().getName().equals("groovy.lang.MetaClass"));

        fields.removeIf(filter);

        return fields;
    }

    /**
     * Returns value of the field.
     *
     * @param instance instance if field is static, null
     * @param field    targeted field
     * @return field value
     * @throws RuntimeException if get value from the field
     */
    @Nullable
    public static Object getFieldValue(@Nullable Object instance, Field field) {
        Asserts.that(field).isNotNull();
        if (!Modifier.isStatic(field.getModifiers())) Asserts.that(instance).isNotNull();

        // Enables to have access to the field even private field.
        boolean accessible = field.isAccessible();
        if (!accessible) field.setAccessible(true);

        try {
            // Returns value in the field.
            return field.get(instance);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to get value from the field(%s) of the class(%s)",
                    field.getName(), field.getDeclaringClass().getName());
            throw new RuntimeException(message, e);
        } finally {
            // Rolls back the accessibility of the field as it was.
            field.setAccessible(accessible);
        }
    }

    /**
     * Sets up value into the field.
     *
     * @param instance instance if method is static, null
     * @param field    targeted field
     * @param value    value to be set into field
     * @throws RuntimeException if failed to set value into the field
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
            // Sets value into the field.
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to set value into the field(%s) of the class(%s)",
                    field.getName(), field.getDeclaringClass().getName());
            throw new RuntimeException(message, e);
        } finally {
            // Rolls back the accessibility of the field as it was.
            field.setAccessible(accessible);
        }
    }

    /**
     * Returns the specific constructor declared by given type.
     *
     * @param type       class
     * @param paramTypes parameter types of constructor
     * @param <T>        type of instance
     * @return constructor
     */
    public static <T> Constructor<T> getDeclaredConstructor(Class<T> type, @Nullable Class<?>... paramTypes) {
        Asserts.that(type).isNotNull();
        if (paramTypes != null) Asserts.that(paramTypes).doesNotContainNull();

        try {
            // Gets constructor with the specific parameter types.
            return type.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            String message = String.format("Cannot find a constructor: %s(%s)",
                    type, Arrays.toString(paramTypes).replaceAll("\\[|]", ""));
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Returns instance of type using default constructor.
     *
     * @param type class
     * @param <T>  type of instance
     * @return instance of type
     * @throws RuntimeException if the type doesn't have default constructor
     * @throws RuntimeException if failed to instantiate
     */
    public static <T> T instantiate(Class<T> type) {
        return instantiate(type, null, null);
    }

    /**
     * Returns instance of type using the constructor.
     *
     * @param constructor constructor declared in type
     * @param initArgs    initial arguments of constructor
     * @param <T>         type of instance
     * @return instance of type
     * @throws RuntimeException if the type doesn't have matched constructor
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
            // Rolls back the accessibility of the constructor as it was.
            constructor.setAccessible(accessible);
        }
    }

    /**
     * Returns the specific method declared by given type.
     *
     * @param type       class
     * @param methodName name of method
     * @param paramTypes parameter types of method
     * @return method
     */
    public static Method getDeclaredMethod(Class<?> type, String methodName, @Nullable Class<?>... paramTypes) {
        Asserts.that(type).isNotNull();
        Asserts.that(methodName).isNotNull().hasText();
        if (paramTypes != null) Asserts.that(paramTypes).doesNotContainNull();

        try {
            return type.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes the method and returns its result.
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
            // Rolls back the accessibility of the method as it was.
            method.setAccessible(accessible);
        }
    }

    /**
     * Runs the executable and returns its result.
     *
     * @param executable constructor or method
     * @param instance   instance if {@code executable} is not instance method, null
     * @param args       arguments of the executable
     * @return result of the executable
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

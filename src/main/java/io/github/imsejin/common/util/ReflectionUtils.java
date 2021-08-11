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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // Removes a instance of outer class when the type is non-static inner class.
        if (!Modifier.isStatic(type.getModifiers()) && (type.isMemberClass() || type.isLocalClass())) {
            fields.remove(0);
        }

        // Removes static fields from list.
        fields.removeIf(it -> Modifier.isStatic(it.getModifiers()));

        return fields;
    }

    /**
     * Returns value of the field.
     *
     * @param model model in list
     * @param field targeted field
     * @param <T>   type of the object
     * @return field value
     * @throws RuntimeException if get value from the field
     */
    @Nullable
    public static <T> Object getFieldValue(T model, Field field) {
        Asserts.that(model).isNotNull();
        Asserts.that(field).isNotNull();

        // Enables to have access to the field even private field.
        field.setAccessible(true);

        try {
            // Returns value in the field.
            return field.get(model);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to get value from the field(%s) of the class(%s)",
                    field.getName(), model.getClass().getName());
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Sets up value into the field.
     *
     * @param model model in list
     * @param field targeted field
     * @param value value to be set into field
     * @param <T>   type of the object
     * @throws RuntimeException if failed to set value into the field
     */
    public static <T> void setFieldValue(T model, Field field, Object value) {
        Asserts.that(model).isNotNull();
        Asserts.that(field).isNotNull();

        // Enables to have access to the field even private field.
        field.setAccessible(true);

        // Sets value into the field.
        try {
            field.set(model, value);
        } catch (IllegalAccessException e) {
            String message = String.format("Failed to set value into the field(%s) of the class(%s)",
                    field.getName(), model.getClass().getName());
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
     * Returns instance of type using the specific constructor.
     *
     * @param type       class
     * @param paramTypes parameter types of constructor
     * @param initArgs   initial arguments of constructor
     * @param <T>        type of instance
     * @return instance of type
     * @throws RuntimeException if the type doesn't have default constructor
     * @throws RuntimeException if failed to instantiate
     */
    public static <T> T instantiate(Class<T> type, @Nullable Class<?>[] paramTypes, @Nullable Object[] initArgs) {
        Asserts.that(type).isNotNull();
        if (paramTypes != null) Asserts.that(paramTypes).doesNotContainNull().isSameLength(initArgs);
        if (initArgs != null) Asserts.that(initArgs).isSameLength(paramTypes);

        Constructor<T> constructor;
        try {
            // Gets constructor with the specific parameter types.
            constructor = type.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            String message = String.format("Cannot find a constructor: %s(%s)",
                    type, Arrays.toString(paramTypes).replaceAll("\\[|]", ""));
            throw new RuntimeException(message, e);
        }
        constructor.setAccessible(true);

        // Instantiates new model and sets up data into the model's fields.
        try {
            return constructor.newInstance(initArgs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String message = String.format("Failed to instantiate by constructor: %s(%s)",
                    type, Arrays.toString(paramTypes).replaceAll("\\[|]", ""));
            throw new RuntimeException(message, e);
        }
    }

}

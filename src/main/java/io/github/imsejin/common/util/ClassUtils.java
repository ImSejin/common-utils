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

import java.lang.reflect.Modifier;

/**
 * Class utilities
 */
public final class ClassUtils {

    @ExcludeFromGeneratedJacocoReport
    private ClassUtils() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
    }

    /**
     * Checks if this class is enum or its one of a constant.
     * <p>
     * A class that extends {@link Enum} and its constants are changed internally since JDK 9.
     *
     * <pre>
     * 1. A class that extends {@link Enum}: {@link java.util.concurrent.TimeUnit}
     * +--------+--------+----------------+----------+-------+
     * | JDK \  | isEnum | getSuperclass  | abstract | final |
     * +--------+--------+----------------+----------+-------+
     * |  8 >=  | true   | java.lang.Enum | true     | false |
     * +--------+--------+----------------+----------+-------+
     * |  9 <=  | true   | java.lang.Enum | false    | true  |
     * +--------+--------+----------------+----------+-------+
     *
     * 2. A class that has no body, declared in enum class body as a constant: {@link java.time.Month#JANUARY}
     * +--------+--------+----------------+----------+-------+
     * | JDK \  | isEnum | getSuperclass  | abstract | final |
     * +--------+--------+----------------+----------+-------+
     * |  8 >=  | true   | java.lang.Enum | false    | true  |
     * +--------+--------+----------------+----------+-------+
     * |  9 <=  | true   | java.lang.Enum | false    | true  |
     * +--------+--------+----------------+----------+-------+
     *
     * 3. A class that has own body, declared in enum class body as a constant: {@link java.util.concurrent.TimeUnit#HOURS}
     * +--------+--------+-------------------------------+----------+-------+
     * | JDK \  | isEnum | getSuperclass                 | abstract | final |
     * +--------+--------+-------------------------------+----------+-------+
     * |  8 >=  | false  | java.util.concurrent.TimeUnit | false    | false |
     * +--------+--------+-------------------------------+----------+-------+
     * |  9 <=  | true   | java.lang.Enum                | false    | true  |
     * +--------+--------+-------------------------------+----------+-------+
     * </pre>
     *
     * @param clazz class
     * @return whether this class is enum or its one of a constant.
     * @see Class#isEnum()
     * @see Enum#getDeclaringClass()
     */
    public static boolean isEnumOrEnumConstant(Class<?> clazz) {
        if (clazz == null) return false;

        // JDK 9 later
        if (clazz.isEnum()) return true;

        // JDK 8 for enum constant as an anonymous class.
        for (Class<?> c = clazz.getSuperclass(); c != null; c = c.getSuperclass()) {
            if (c.isEnum()) return true;
        }

        return false;
    }

    /**
     * Checks if this class is abstract.
     *
     * @param clazz class
     * @return whether this class is abstract
     */
    public static boolean isAbstractClass(Class<?> clazz) {
        if (clazz == null) return false;

        final int modifiers = clazz.getModifiers();
        return Modifier.isAbstract(modifiers) &&
                !Modifier.isFinal(modifiers) && // Keyword 'abstract' is cannot be compatible with 'final' (exclude array class).
                !clazz.isPrimitive() && !clazz.isInterface() && !isEnumOrEnumConstant(clazz);
    }

}

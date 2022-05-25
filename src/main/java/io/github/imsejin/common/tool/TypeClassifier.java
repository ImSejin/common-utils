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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Type classifier
 */
public final class TypeClassifier {

    @ExcludeFromGeneratedJacocoReport
    private TypeClassifier() {
        throw new UnsupportedOperationException(getClass().getName() + " is not allowed to instantiate");
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

        public Set<Class<?>> getClasses() {
            return this.classes;
        }
    }

}

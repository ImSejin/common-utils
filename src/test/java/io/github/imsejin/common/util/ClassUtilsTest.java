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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.constant.DateType;
import io.github.imsejin.common.tool.TypeClassifier;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class ClassUtilsTest {

    private static final List<Class<?>> INTERFACES = Arrays.asList(
            CharSequence.class, Function.class, Member.class, Annotation.class);
    private static final List<Class<?>> ANNOTATIONS = Arrays.asList(
            Override.class, SuppressWarnings.class, SafeVarargs.class, FunctionalInterface.class);
    private static final List<Class<?>> ENUMS = Arrays.asList(
            TimeUnit.class, DateType.class, DayOfWeek.class, Month.class);
    private static final List<Class<?>> ENUM_CONSTANTS = Stream.concat(
            EnumSet.allOf(TimeUnit.class).stream(), EnumSet.allOf(Month.class).stream())
            .map(Object::getClass).collect(toList());
    private static final List<Class<?>> ABSTRACT_CLASSES = Arrays.asList(
            OutputStream.class, EnumSet.class, Asserts.class, Number.class);
    private static final List<Class<?>> ANONYMOUS_CLASSES = Arrays.asList(
            new Asserts() {
            }.getClass(),
            new Random() {
            }.getClass());
    private static final List<Class<?>> ARRAYS = Arrays.asList(
            int[].class, Member[].class, Override[].class, Month[].class, Number[].class);

    @Test
    void isEnumOrEnumConstant() {
        List<Class<?>> enumAndConstantClasses = Stream.of(ENUMS, ENUM_CONSTANTS)
                .flatMap(Collection::stream).collect(toList());
        List<Class<?>> classes = Stream.of(
                Collections.singleton((Class<?>) null),
                TypeClassifier.Types.PRIMITIVE.getClasses(),
                TypeClassifier.Types.WRAPPER.getClasses(),
                INTERFACES, ANNOTATIONS, ANONYMOUS_CLASSES, ARRAYS)
                .flatMap(Collection::stream).collect(toList());

        enumAndConstantClasses.forEach(clazz -> assertThat(ClassUtils.isEnumOrEnumConstant(clazz))
                .as("this is enum or enum constant class: '%s'", clazz).isTrue());
        classes.forEach(clazz -> assertThat(ClassUtils.isEnumOrEnumConstant(clazz))
                .as("this is neither enum nor enum constant class: '%s'", clazz).isFalse());
    }

    @Test
    void isAbstractClass() {
        List<Class<?>> nonAbstractClasses = Stream.of(
                Collections.singleton((Class<?>) null),
                TypeClassifier.Types.PRIMITIVE.getClasses(),
                TypeClassifier.Types.WRAPPER.getClasses(),
                INTERFACES, ANNOTATIONS, ENUMS, ENUM_CONSTANTS, ANONYMOUS_CLASSES, ARRAYS)
                .flatMap(Collection::stream).collect(toList());

        ABSTRACT_CLASSES.forEach(clazz -> assertThat(ClassUtils.isAbstractClass(clazz))
                .as("this is abstract class: '%s'", clazz).isTrue());
        nonAbstractClasses.forEach(clazz -> assertThat(ClassUtils.isAbstractClass(clazz))
                .as("this is not abstract class: '%s'", clazz).isFalse());
    }

}

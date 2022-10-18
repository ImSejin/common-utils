/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.constant.DateType;
import io.github.imsejin.common.tool.Stopwatch;
import io.github.imsejin.common.tool.TypeClassifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.nio.file.AccessMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ClassAssert")
class ClassAssertTest {

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

    @Nested
    @DisplayName("method 'isAssignableFrom'")
    class IsAssignableFrom {
        @Test
        @DisplayName("passes, when actual is assignable from the given type")
        void test0() {
            // given
            Map<Class<?>, Class<?>> map = new HashMap<>();
            map.put(Number.class, Long.class);
            map.put(Object.class, String.class);
            map.put(CharSequence.class, StringBuffer.class);
            map.put(Map.class, ConcurrentMap.class);
            map.put(Class.class, Class.class);
            map.put(Member.class, Field.class);
            map.put(Descriptor.class, ClassAssert.class);

            // expect
            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isAssignableFrom(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not assignable from the given type")
        void test1() {
            // given
            Map<Class<?>, Class<?>> map = new HashMap<>();
            map.put(Long.class, long.class);
            map.put(int.class, Integer.class);
            map.put(String.class, CharSequence.class);
            map.put(ConcurrentMap.class, Map.class);
            map.put(Class.class, Object.class);
            map.put(Field.class, Member.class);
            map.put(ClassAssert.class, Descriptor.class);

            // expect
            map.forEach((actual, expected) -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isAssignableFrom(expected))
                    .withMessageMatching(Pattern.quote("It is expected to be assignable from the given type, but it isn't.") +
                            "\n {4}actual: '.+'" +
                            "\n {4}expected: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSuperclassOf'")
    class IsSuperclassOf {
        @Test
        @DisplayName("passes, when actual is superclass of the given type")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(Throwable.class).isSuperclassOf(Exception.class);
                Asserts.that(Exception.class).isSuperclassOf(RuntimeException.class);
                Asserts.that(RuntimeException.class).isSuperclassOf(IllegalArgumentException.class);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not superclass of the given type")
        void test1() {
            String message = Pattern.quote("It is expected to be superclass of the given type, but it isn't.")+
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that((Class<?>) null)
                    .isSuperclassOf(Object.class))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IllegalArgumentException.class)
                    .isSuperclassOf(RuntimeException.class))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(RuntimeException.class)
                    .isSuperclassOf(Exception.class))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Exception.class)
                    .isSuperclassOf(Throwable.class))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isSubclassOf'")
    class IsSubclassOf {
        @Test
        @DisplayName("passes, when actual is subclass of the given type")
        void test0() {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(IllegalArgumentException.class).isSubclassOf(RuntimeException.class);
                Asserts.that(RuntimeException.class).isSubclassOf(Exception.class);
                Asserts.that(Exception.class).isSubclassOf(Throwable.class);
            });
        }

        @Test
        @DisplayName("throws exception, when actual is not subclass of the given type")
        void test1() {
            String message = Pattern.quote("It is expected to be subclass of the given type, but it isn't.")+
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.+'";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Object.class)
                    .isSubclassOf(null))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Throwable.class)
                    .isSubclassOf(Exception.class))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Exception.class)
                    .isSubclassOf(RuntimeException.class))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(RuntimeException.class)
                    .isSubclassOf(IllegalArgumentException.class))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPrimitive'")
    class IsPrimitive {
        @Test
        @DisplayName("passes, when actual is primitive")
        void test0() {
            assertThatNoException().isThrownBy(() -> TypeClassifier.Types.PRIMITIVE.getClasses()
                    .forEach(actual -> Asserts.that(actual).isPrimitive()));
        }

        @Test
        @DisplayName("throws exception, when actual is not primitive")
        void test1() {
            TypeClassifier.Types.WRAPPER.getClasses()
                    .forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                            .isPrimitive())
                            .withMessageMatching(Pattern.quote("It is expected to be primitive, but it isn't.") +
                                    "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isInterface'")
    class IsInterface {
        @Test
        @DisplayName("passes, when actual is interface")
        void test0() {
            // given
            List<Class<?>> classes = Stream.of(INTERFACES, ANNOTATIONS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            assertThatNoException().isThrownBy(() -> classes
                    .forEach(actual -> Asserts.that(actual).isInterface()));
        }

        @Test
        @DisplayName("throws exception, when actual is not interface")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), ENUMS,
                    ENUM_CONSTANTS, ABSTRACT_CLASSES, ANONYMOUS_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isInterface())
                    .withMessageMatching(Pattern.quote("It is expected to be interface, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAnnotation'")
    class IsAnnotation {
        @Test
        @DisplayName("passes, when actual is annotation")
        void test0() {
            assertThatNoException().isThrownBy(() -> ANNOTATIONS
                    .forEach(actual -> Asserts.that(actual).isAnnotation()));
        }

        @Test
        @DisplayName("throws exception, when actual is not annotation")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), INTERFACES, ENUMS,
                    ENUM_CONSTANTS, ABSTRACT_CLASSES, ANONYMOUS_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isAnnotation())
                    .withMessageMatching(Pattern.quote("It is expected to be annotation, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isFinalClass()'")
    class isFinalClass {
        @Test
        @DisplayName("passes, when actual is final class")
        void test0() {
            // given
            List<Class<?>> classes = Arrays.asList(String.class, Double.class, int.class,
                    LocalDate.class, Stopwatch.class, AccessMode.READ.getClass());

            assertThatNoException().isThrownBy(() -> classes
                    .forEach(actual -> Asserts.that(actual).isFinalClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not final class")
        void test1() {
            // given
            List<Class<?>> classes = Arrays.asList(CharSequence.class, Number.class, Object.class,
                    ChronoLocalDate.class, Enum.class, Asserts.class);

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isFinalClass())
                    .withMessageMatching(Pattern.quote("It is expected to be final class, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAbstractClass'")
    class IsAbstractClass {
        @Test
        @DisplayName("passes, when actual is abstract class")
        void test0() {
            assertThatNoException().isThrownBy(() -> ABSTRACT_CLASSES
                    .forEach(actual -> Asserts.that(actual).isAbstractClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not abstract class")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), INTERFACES,
                    ANNOTATIONS, ENUMS, ENUM_CONSTANTS, ANONYMOUS_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isAbstractClass())
                    .withMessageMatching(Pattern.quote("It is expected to be abstract class, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAnonymousClass'")
    class IsAnonymousClass {
        @Test
        @DisplayName("passes, when actual is anonymous class")
        void test0() {
            assertThatNoException().isThrownBy(() -> ANONYMOUS_CLASSES
                    .forEach(actual -> Asserts.that(actual).isAnonymousClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not anonymous class")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), INTERFACES,
                    ANNOTATIONS, ENUMS, ABSTRACT_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            System.out.printf("Enum constant that has no body is anonymous class? %s%n",
                    Month.JANUARY.getClass().isAnonymousClass());
            System.out.printf("Enum constant that has a body is anonymous class? %s%n",
                    TimeUnit.DAYS.getClass().isAnonymousClass());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isAnonymousClass())
                    .withMessageMatching(Pattern.quote("It is expected to be anonymous class, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isEnum'")
    class IsEnum {
        @Test
        @DisplayName("passes, when actual is enum")
        void test0() {
            // given
            List<Class<?>> classes = Stream.of(ENUMS, ENUM_CONSTANTS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            assertThatNoException().isThrownBy(() -> classes
                    .forEach(actual -> Asserts.that(actual).isEnum()));
        }

        @Test
        @DisplayName("throws exception, when actual is not enum")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), INTERFACES,
                    ANNOTATIONS, ABSTRACT_CLASSES, ANONYMOUS_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isEnum())
                    .withMessageMatching(Pattern.quote("It is expected to be enum, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isArray'")
    class IsArray {
        @Test
        @DisplayName("passes, when actual is array")
        void test0() {
            assertThatNoException().isThrownBy(() -> ARRAYS
                    .forEach(actual -> Asserts.that(actual).isArray()));
        }

        @Test
        @DisplayName("throws exception, when actual is not array")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(), INTERFACES,
                    ANNOTATIONS, ENUMS, ENUM_CONSTANTS, ABSTRACT_CLASSES, ANONYMOUS_CLASSES)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isArray())
                    .withMessageMatching(Pattern.quote("It is expected to be array, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isMemberClass'")
    class IsMemberClass {
        @Test
        @DisplayName("passes, when actual is member class")
        void test0() {
            // given
            List<Class<?>> memberClasses = Arrays.asList(ClassAssertTest.class.getDeclaredClasses());

            // expect
            assertThatNoException().isThrownBy(() -> memberClasses
                    .forEach(actual -> Asserts.that(actual).isMemberClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not member class")
        void test1() {
            // given
            List<Class<?>> classes = Stream.of(
                    TypeClassifier.Types.PRIMITIVE.getClasses(),
                    TypeClassifier.Types.WRAPPER.getClasses(),
                    INTERFACES, ANNOTATIONS, ENUMS, ENUM_CONSTANTS,
                    ABSTRACT_CLASSES, ANONYMOUS_CLASSES, ARRAYS)
                    .flatMap(Collection::stream).collect(toList());

            // expect
            classes.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isMemberClass())
                    .withMessageMatching(Pattern.quote("It is expected to be member class, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isLocalClass'")
    class IsLocalClass {
        @Test
        @DisplayName("passes, when actual is local class")
        void test0() {
            // given
            abstract class A {
                public abstract void doIt(Object it);
            }
            class B extends A {
                public void doIt(Object it) {
                    System.out.println(it);
                }
            }

            List<Class<?>> localClasses = Arrays.asList(A.class, B.class);

            // expect
            assertThatNoException().isThrownBy(() -> localClasses
                    .forEach(actual -> Asserts.that(actual).isLocalClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not local class")
        void test1() {
            // given
            List<Class<?>> memberClasses = Arrays.asList(ClassAssertTest.class.getDeclaredClasses());

            // expect
            memberClasses.forEach(actual -> assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(actual)
                    .isLocalClass())
                    .withMessageMatching(Pattern.quote("It is expected to be local class, but it isn't.") +
                            "\n {4}actual: '.+'"));
        }
    }

}

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
import io.github.imsejin.common.tool.TypeClassifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
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
    @DisplayName("method 'isTypeOf'")
    class IsTypeOf {
        @Test
        @DisplayName("passes, when actual is type of the given instance")
        void test0() throws NoSuchFieldException {
            // given
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512L);
            map.put(Object.class, new Object());
            map.put(StringBuffer.class, new StringBuffer("string-buffer"));
            map.put(CharSequence.class, String.class.getName());
            map.put(Supplier.class, (Supplier<Map<?, ?>>) HashMap::new);
            map.put(Class.class, getClass());
            map.put(Member.class, String.class.getDeclaredField("value"));
            map.put(Descriptor.class, Asserts.that(new Object()));

            // except
            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isTypeOf(expected)));
        }

        @Test
        void test1() throws NoSuchFieldException {
            // given
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512);
            map.put(Object.class, null);
            map.put(StringBuffer.class, new StringBuilder("string-builder"));
            map.put(CharSequence.class, new Object());
            map.put(Supplier.class, (Function<Collection<?>, List<?>>) ArrayList::new);
            map.put(Class.class, getClass().getSimpleName());
            map.put(Constructor.class, String.class.getDeclaredField("value"));
            map.put(ClassAssert.class, Asserts.that(new Object()));

            // except
            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isTypeOf(expected))
                    .withMessageStartingWith("It is expected to be type of the instance, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotTypeOf'")
    class IsNotTypeOf {
        @Test
        @DisplayName("passes, when actual is not type of the given instance")
        void test0() throws NoSuchFieldException {
            // given
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512);
            map.put(Object.class, null);
            map.put(StringBuffer.class, new StringBuilder("string-builder"));
            map.put(CharSequence.class, new Object());
            map.put(Supplier.class, (Function<Collection<?>, List<?>>) ArrayList::new);
            map.put(Class.class, getClass().getSimpleName());
            map.put(Constructor.class, String.class.getDeclaredField("value"));
            map.put(ClassAssert.class, Asserts.that(new Object()));

            // except
            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isNotTypeOf(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is type of the given instance")
        void test1() throws NoSuchFieldException {
            // given
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512L);
            map.put(Object.class, new Object());
            map.put(StringBuffer.class, new StringBuffer("string-buffer"));
            map.put(CharSequence.class, String.class.getName());
            map.put(Supplier.class, (Supplier<Map<?, ?>>) HashMap::new);
            map.put(Class.class, getClass());
            map.put(Member.class, String.class.getDeclaredField("value"));
            map.put(Descriptor.class, Asserts.that(new Object()));

            // except
            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotTypeOf(expected))
                    .withMessageStartingWith("It is expected not to be type of the instance, but it is."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
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

            // except
            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAssignableFrom(expected))
                    .withMessageStartingWith("It is expected to be assignable from the given type, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
            String description = "It is expected to be superclass of the given type, but it isn't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(IllegalArgumentException.class)
                    .isSuperclassOf(RuntimeException.class)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(RuntimeException.class)
                    .isSuperclassOf(Exception.class)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Exception.class)
                    .isSuperclassOf(Throwable.class)).withMessageStartingWith(description);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
            String description = "It is expected to be subclass of the given type, but it isn't.";

            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Throwable.class)
                    .isSubclassOf(Exception.class)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(Exception.class)
                    .isSubclassOf(RuntimeException.class)).withMessageStartingWith(description);
            assertThatIllegalArgumentException().isThrownBy(() -> Asserts.that(RuntimeException.class)
                    .isSubclassOf(IllegalArgumentException.class)).withMessageStartingWith(description);

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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
                    .forEach(actual -> assertThatIllegalArgumentException()
                            .isThrownBy(() -> Asserts.that(actual).isPrimitive())
                            .withMessageStartingWith("It is expected to be primitive, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isInterface'")
    class IsInterface {
        @Test
        @DisplayName("passes, when actual is interface")
        void test0() {
            // given
            List<Class<?>> classes = Stream.of(INTERFACES, ANNOTATIONS)
                    .flatMap(Collection::stream).collect(toList());

            // except
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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isInterface())
                    .withMessageStartingWith("It is expected to be interface, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAnnotation())
                    .withMessageStartingWith("It is expected to be annotation, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAbstractClass())
                    .withMessageStartingWith("It is expected to be abstract class, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isAnonymousClass())
                    .withMessageStartingWith("It is expected to be anonymous class, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isEnum'")
    class IsEnum {
        @Test
        @DisplayName("passes, when actual is enum")
        void test0() {
            // given
            List<Class<?>> classes = Stream.of(ENUMS, ENUM_CONSTANTS)
                    .flatMap(Collection::stream).collect(toList());

            // except
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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isEnum())
                    .withMessageStartingWith("It is expected to be enum, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isArray())
                    .withMessageStartingWith("It is expected to be array, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isMemberClass'")
    class IsMemberClass {
        @Test
        @DisplayName("passes, when actual is member class")
        void test0() {
            // given
            List<Class<?>> memberClasses = Arrays.asList(ClassAssertTest.class.getDeclaredClasses());

            // except
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

            // except
            classes.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isMemberClass())
                    .withMessageStartingWith("It is expected to be member class, but it isn't."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

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

            // except
            assertThatNoException().isThrownBy(() -> localClasses
                    .forEach(actual -> Asserts.that(actual).isLocalClass()));
        }

        @Test
        @DisplayName("throws exception, when actual is not local class")
        void test1() {
            // given
            List<Class<?>> memberClasses = Arrays.asList(ClassAssertTest.class.getDeclaredClasses());

            // except
            memberClasses.forEach(actual -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isLocalClass())
                    .withMessageStartingWith("It is expected to be local class, but it isn't."));
        }
    }

}

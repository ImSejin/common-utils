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

package io.github.imsejin.common.assertion.reflect;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("ClassAssert")
class ClassAssertTest {

    @Nested
    @DisplayName("method 'isTypeOf'")
    class IsTypeOf {
        @Test
        @DisplayName("passes, when actual is type of the given instance")
        void test0() throws NoSuchFieldException {
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512L);
            map.put(Object.class, new Object());
            map.put(StringBuffer.class, new StringBuffer("string-buffer"));
            map.put(CharSequence.class, String.class.getName());
            map.put(Supplier.class, (Supplier<Map<?, ?>>) HashMap::new);
            map.put(Class.class, getClass());
            map.put(Member.class, String.class.getDeclaredField("value"));
            map.put(Descriptor.class, Asserts.that(new Object()));

            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isTypeOf(expected)));
        }

        @Test
        void test1() throws NoSuchFieldException {
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512);
            map.put(Object.class, null);
            map.put(StringBuffer.class, new StringBuilder("string-builder"));
            map.put(CharSequence.class, new Object());
            map.put(Supplier.class, (Function<Collection<?>, List<?>>) ArrayList::new);
            map.put(Class.class, getClass().getSimpleName());
            map.put(Constructor.class, String.class.getDeclaredField("value"));
            map.put(ClassAssert.class, Asserts.that(new Object()));

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
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512);
            map.put(Object.class, null);
            map.put(StringBuffer.class, new StringBuilder("string-builder"));
            map.put(CharSequence.class, new Object());
            map.put(Supplier.class, (Function<Collection<?>, List<?>>) ArrayList::new);
            map.put(Class.class, getClass().getSimpleName());
            map.put(Constructor.class, String.class.getDeclaredField("value"));
            map.put(ClassAssert.class, Asserts.that(new Object()));

            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isNotTypeOf(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is type of the given instance")
        void test1() throws NoSuchFieldException {
            Map<Class<?>, Object> map = new HashMap<>();
            map.put(long.class, 512L);
            map.put(Object.class, new Object());
            map.put(StringBuffer.class, new StringBuffer("string-buffer"));
            map.put(CharSequence.class, String.class.getName());
            map.put(Supplier.class, (Supplier<Map<?, ?>>) HashMap::new);
            map.put(Class.class, getClass());
            map.put(Member.class, String.class.getDeclaredField("value"));
            map.put(Descriptor.class, Asserts.that(new Object()));

            map.forEach((actual, expected) -> assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(actual).isNotTypeOf(expected))
                    .withMessageStartingWith("It is expected to be not type of the instance, but it is."));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAssignableFrom'")
    class IsAssignableFrom {
        @Test
        @DisplayName("passes, when actual is assignable from the given type")
        void test0() {
            Map<Class<?>, Class<?>> map = new HashMap<>();
            map.put(Number.class, Long.class);
            map.put(Object.class, String.class);
            map.put(CharSequence.class, StringBuffer.class);
            map.put(Map.class, ConcurrentMap.class);
            map.put(Class.class, Class.class);
            map.put(Member.class, Field.class);
            map.put(Descriptor.class, ClassAssert.class);

            assertThatNoException().isThrownBy(() -> map
                    .forEach((actual, expected) -> Asserts.that(actual).isAssignableFrom(expected)));
        }

        @Test
        @DisplayName("throws exception, when actual is not assignable from the given type")
        void test1() {
            Map<Class<?>, Class<?>> map = new HashMap<>();
            map.put(Long.class, long.class);
            map.put(int.class, Integer.class);
            map.put(String.class, CharSequence.class);
            map.put(ConcurrentMap.class, Map.class);
            map.put(Class.class, Object.class);
            map.put(Field.class, Member.class);
            map.put(ClassAssert.class, Descriptor.class);

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
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isSubclassOf'")
    class IsSubclassOf {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isPrimitive'")
    class IsPrimitive {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isInterface'")
    class IsInterface {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAnnotation'")
    class IsAnnotation {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAbstractClass'")
    class IsAbstractClass {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isAnonymousClass'")
    class IsAnonymousClass {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isEnum'")
    class IsEnum {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isArray'")
    class IsArray {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isMemberClass'")
    class IsMemberClass {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isLocalClass'")
    class IsLocalClass {
        @Test
        @DisplayName("")
        void test0() {
        }

        @Test
        @DisplayName("")
        void test1() {
        }
    }

}

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
import io.github.imsejin.common.assertion.reflect.ClassAssert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReflectionUtils")
class ReflectionUtilsTest {

    @Test
    @DisplayName("method 'getInheritedFields'")
    void getInheritedFields() throws ClassNotFoundException {
        assertThat(ReflectionUtils.getInheritedFields(Parent.class))
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .map(Field::getName)
                .containsExactly("id", "name", "createdAt", "modifiedAt");

        Class<?> subclass = Class.forName(Parent.class.getName() + "$Child");
        assertThat(ReflectionUtils.getInheritedFields(subclass))
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .map(Field::getName)
                .containsExactly("id", "name", "createdAt", "modifiedAt", "id", "title");

        class C {
        }
        Stream.of(getClass(), A.class, A.AA.class, A.AB.class, B.class, B.BA.class, C.class)
                .forEach(clazz -> assertThat(ReflectionUtils.getInheritedFields(clazz))
                        .isNotNull()
                        .doesNotContainNull()
                        .isEmpty());
    }

    @Test
    @DisplayName("method 'getFieldValue'")
    void getFieldValue() throws NoSuchFieldException {
        // given: 1
        @RequiredArgsConstructor
        class Model {
            private final long id;
        }
        Model model = new Model(new Random().nextLong());
        Field field0 = model.getClass().getDeclaredField("id");
        // when: 1
        Object fieldValue0 = ReflectionUtils.getFieldValue(model, field0);
        // then: 1
        assertThat(fieldValue0)
                .isNotNull().isNotEqualTo(0L).isEqualTo(model.id);

        // given: 2
        A.id = new Random().nextLong();
        Field field1 = A.class.getDeclaredField("id");
        // when: 2
        Object fieldValue1 = ReflectionUtils.getFieldValue(null, field1);
        // then: 2
        assertThat(fieldValue1)
                .isNotNull().isNotEqualTo(0L).isEqualTo(A.id);
        A.id = null;
    }

    @Test
    @DisplayName("method 'setFieldValue'")
    void setFieldValue() throws NoSuchFieldException {
        // given: 1
        class Model {
            private String name;
        }
        Model model = new Model();
        Field field0 = model.getClass().getDeclaredField("name");
        assertThat(ReflectionUtils.getFieldValue(model, field0)).isNull();
        // when: 1
        ReflectionUtils.setFieldValue(model, field0, UUID.randomUUID().toString());
        // then: 1
        assertThat(ReflectionUtils.getFieldValue(model, field0))
                .isNotNull().isEqualTo(model.name);

        // given: 2
        Field field1 = A.AA.class.getDeclaredField("name");
        assertThat(ReflectionUtils.getFieldValue(null, field1)).isNull();
        // when: 2
        ReflectionUtils.setFieldValue(null, field1, UUID.randomUUID().toString());
        // then: 2
        assertThat(ReflectionUtils.getFieldValue(null, field1))
                .isNotNull().isEqualTo(A.AA.name);
        A.AA.name = null;
    }

    @Test
    @DisplayName("method 'getDeclaredConstructor'")
    void getDeclaredConstructor() throws ClassNotFoundException {
        // given: 1
        Class<Asserts> t0 = Asserts.class;
        // when: 1
        Constructor<Asserts> c0 = ReflectionUtils.getDeclaredConstructor(t0);
        // then: 1
        assertThat(c0.getDeclaringClass()).isEqualTo(t0);

        // given: 2
        Class<?> t1 = Class.forName(Parent.class.getName() + "$Child");
        // when: 2
        Constructor<?> c1 = ReflectionUtils.getDeclaredConstructor(t1, int.class, String.class);
        // then: 2
        assertThat(c1.getDeclaringClass()).isEqualTo(t1);
    }

    @ParameterizedTest
    @DisplayName("method 'instantiate'")
    @ValueSource(classes = {A.class, A.AA.class, A.AB.class, B.class, B.BA.class})
    void instantiate(Class<?> clazz) {
        // given
        Class<?>[] paramTypes = null;
        Object[] initArgs = null;
        if (!Modifier.isStatic(clazz.getModifiers())) {
            paramTypes = new Class[]{clazz.getEnclosingClass()};

            if (clazz == B.BA.class) {
                initArgs = new Object[]{new B()};
            } else {
                Object encloser = getClass() == clazz.getEnclosingClass()
                        ? this : ReflectionUtils.instantiate(clazz.getEnclosingClass());
                initArgs = new Object[]{encloser};
            }
        }

        // when
        Object instant = ReflectionUtils.instantiate(clazz, paramTypes, initArgs);

        // then
        assertThat(instant)
                .isNotNull()
                .isExactlyInstanceOf(clazz);
    }

    @Test
    @DisplayName("method 'getDeclaredMethod'")
    void getDeclaredMethod() throws ClassNotFoundException {
        // given: 1
        Class<Asserts> t0 = Asserts.class;
        // when: 1
        Method m0 = ReflectionUtils.getDeclaredMethod(t0, "that", Class.class);
        // then: 1
        assertThat(m0)
                .isNotNull().returns(t0, Method::getDeclaringClass)
                .returns(ClassAssert.class, it -> invokeMethod(it, null, Object.class).getClass());

        // given: 2
        Class<?> t1 = Class.forName(Parent.class.getName() + "$Child");
        // when: 2
        Method m1 = ReflectionUtils.getDeclaredMethod(t1, "getId");
        // then: 2
        Object instance = ReflectionUtils.instantiate(t1, new Class<?>[]{int.class, String.class}, new Object[]{256, "smith"});
        assertThat(m1)
                .isNotNull().returns(t1, Method::getDeclaringClass)
                .returns(256, it -> invokeMethod(it, instance));
    }

    @Test
    @DisplayName("method 'invoke'")
    void invoke() {
        // given: 1
        List<Character> characters = Arrays.asList('0', '1', '2');
        // when: 1
        Object size = ReflectionUtils.invoke(Collection.class, characters, "size", null, null);
        // then: 1
        assertThat(size)
                .isNotNull().isExactlyInstanceOf(Integer.class)
                .isEqualTo(characters.size());

        // given: 2
        Class<?>[] paramTypes = {Object.class};
        Object[] args = {"a element in singleton list"};
        // when: 2
        Object singletonList = ReflectionUtils.invoke(Collections.class, null, "singletonList", paramTypes, args);
        // then: 2
        assertThat(singletonList)
                .isNotNull().returns(true, it -> List.class.isAssignableFrom(it.getClass()))
                .isEqualTo(Collections.singletonList(args[0]));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private static Object invokeMethod(Method method, @Nullable Object instance, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static class A {
        private static Long id;

        private static class AA {
            private static String name;
        }

        private class AB {
        }
    }

    private class B {
        private class BA {
        }
    }

}

class Parent implements Serializable {

    private static final long serialVersionUID = 8099680797687427324L;
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Child extends Parent {
        private final int id;
        private final String title;
    }

}

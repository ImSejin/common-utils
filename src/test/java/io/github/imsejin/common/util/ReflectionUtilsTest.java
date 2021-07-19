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

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionUtilsTest {

    private static class A {
        private static class AA {
        }

        private class AB {
        }
    }

    private class B {
        private class BA {
        }
    }

    @Test
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
    void getFieldValue() throws NoSuchFieldException {
        // given
        @RequiredArgsConstructor
        class Model {
            private final long id;
        }

        Model model = new Model(new Random().nextLong());
        Field field = model.getClass().getDeclaredField("id");

        // when & then
        assertThat(ReflectionUtils.getFieldValue(model, field))
                .isNotNull()
                .isNotEqualTo(0L)
                .isEqualTo(model.id);
    }

    @Test
    void setFieldValue() throws NoSuchFieldException {
        // given
        class Model {
            private String name;
        }

        Model model = new Model();
        Field field = model.getClass().getDeclaredField("name");
        assertThat(ReflectionUtils.getFieldValue(model, field)).isNull();
        String fieldValue = UUID.randomUUID().toString();

        // when
        ReflectionUtils.setFieldValue(model, field, fieldValue);

        // then
        assertThat(ReflectionUtils.getFieldValue(model, field))
                .isNotNull().isEqualTo(fieldValue);
    }

    @Test
    void instantiate() {
        Stream.of(A.class, A.AA.class)
                .forEach(clazz -> assertThat(ReflectionUtils.instantiate(clazz))
                        .isNotNull()
                        .isExactlyInstanceOf(clazz));
    }

}

class Parent implements Serializable {

    private static final long serialVersionUID = 8099680797687427324L;
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private static class Child extends Parent {
        private int id;
        private String title;
    }

}

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

package io.github.imsejin.common.assertion;

import io.github.imsejin.common.internal.assertion.ExtendedFooAssert;
import io.github.imsejin.common.internal.assertion.FooAssert;
import io.github.imsejin.common.internal.assertion.KanCodeAssert;
import io.github.imsejin.common.internal.assertion.model.Foo;
import io.github.imsejin.common.internal.assertion.model.KanCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class CustomAssertsTest {

    @Test
    @DisplayName("Fully extensible assertion class")
    void test0() {
        // given
        Foo foo = new Foo();

        // expect
        assertThatNoException().isThrownBy(() -> MyAsserts.that(foo)
                .isNotNull()
                .isEqualTo(foo)
                .hasNullValue());
    }

    @Test
    @DisplayName("Partially extensible assertion class")
    void test1() {
        // given
        Foo foo = new Foo();

        // expect
        assertThatNoException().isThrownBy(() -> new ExtendedFooAssert<>(foo)
                .isNotNull()
                .isEqualTo(foo)
                .hasNullValue());
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ExtendedFooAssert<>(foo)
                        .isNotNull()
                        .isSameAs(foo)
                        .hasSingleValue());
    }

    @Test
    @DisplayName("Restricted assertion class")
    void test2() {
        // given
        KanCode kanCode = new KanCode("01020300");

        // expect
        assertThatNoException().isThrownBy(() -> Asserts.that(kanCode)
                .isNotNull()
                .isEqualTo(new KanCode("01020300"))
                .isNotEqualTo(new KanCode("01020304"))
                .predicate(it -> it.isChildOf(new KanCode("01020000")))
                .returns(true, it -> it.isParentOf(new KanCode("01020304"))));
        assertThatNoException().isThrownBy(() -> MyAsserts.that(kanCode)
                .isNotNull()
                .isEqualTo("01020300")
                .isNotEqualTo("01020304")
                .isChildOf(new KanCode("01020000"))
                .isParentOf(new KanCode("01020304")));
    }

    // -------------------------------------------------------------------------------------------------

    private static class MyAsserts extends Asserts {
        // Don't define return type with wildcard as ACTUAL.
        // public static FooAssert<?, ?> that(Foo foo) {
        public static FooAssert<?, Foo> that(Foo foo) {
            return new FooAssert<>(foo);
        }

        public static KanCodeAssert that(KanCode kanCode) {
            return new KanCodeAssert(kanCode);
        }
    }

}

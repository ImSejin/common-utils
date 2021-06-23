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

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.assertion.Asserts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("BooleanAsserts")
class BooleanAssertsTest {

    @Nested
    @DisplayName("method 'isNull'")
    class IsNull {
        @Test
        @DisplayName("passes, when actual is null")
        void test0() {
            assertThatCode(() -> Asserts.that((Boolean) null).isNull())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("throws exception, when actual is not null")
        void test1() {
            List<Boolean> list = Arrays.asList(true, Boolean.FALSE, new Boolean(true), Boolean.valueOf("false"));

            list.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNull())
                    .hasMessageStartingWith("It is expected to be null, but not null.")
                    .isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isNotNull'")
    class IsNotNull {
        @Test
        @DisplayName("passes, when actual is not null")
        void test0() {
            List<Boolean> list = Arrays.asList(true, Boolean.FALSE, new Boolean(true), Boolean.valueOf("false"));

            list.forEach(actual -> assertThatCode(() -> Asserts.that(actual).isNotNull())
                    .doesNotThrowAnyException());
        }

        @Test
        @DisplayName("throws exception, when actual is null")
        void test1() {
            assertThatCode(() -> Asserts.that((Boolean) null).isNotNull())
                    .hasMessageStartingWith("It is expected to be not null, but null.")
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isTrue'")
    class IsTrue {
        @ParameterizedTest
        @ValueSource(booleans = {true, 1 < 2, false || (0 == 0)})
        @DisplayName("passes, when actual is true")
        void test0(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isTrue())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(booleans = {false, 1 >= 2, false && (0 == 0)})
        @DisplayName("throws exception, when actual is false")
        void test1(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isTrue())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Nested
    @DisplayName("method 'isFalse'")
    class IsFalse {
        @ParameterizedTest
        @ValueSource(booleans = {false, 1 >= 2, false && (0 == 0)})
        @DisplayName("passes, when actual is false")
        void test0(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isFalse())
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(booleans = {true, 1 < 2, false || (0 == 0)})
        @DisplayName("throws exception, when actual is true")
        void test1(boolean actual) {
            assertThatCode(() -> Asserts.that(actual).isFalse())
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

}

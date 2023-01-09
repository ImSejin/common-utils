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

package io.github.imsejin.common.assertion.util;

import java.util.OptionalInt;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.HolderAssertable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("OptionalIntAssert")
class OptionalIntAssertTest {

    @Nested
    @DisplayName("method 'hasValue'")
    class HasValue {
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("passes, when actual has the given content")
        void test0(int value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalInt.of(value))
                    .hasValue(value));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("throws exception, when actual doesn't have the given content")
        void test1(Integer expected) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE) +
                    "\n {4}actual: 'OptionalInt(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalInt.empty()).hasValue(expected)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalInt.of(2)).hasValue(expected)))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveValue'")
    class DoesNotHaveValue {
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("passes, when actual doesn't have the given content")
        void test0(int value) {
            assertThatNoException().isThrownBy(() -> {
                Asserts.that(OptionalInt.empty()).doesNotHaveValue(value);
                Asserts.that(OptionalInt.of(value)).doesNotHaveValue(null);
                Asserts.that(OptionalInt.of(value)).doesNotHaveValue(2);
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("throws exception, when actual has the given content")
        void test1(int value) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE) +
                    "\n {4}actual: 'OptionalInt(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OptionalInt.of(value)).doesNotHaveValue(value))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPresent'")
    class IsPresent {
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("passes, when actual has content")
        void test0(int value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalInt.of(value))
                    .isPresent());
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have content")
        void test1() {
            String message = Pattern.quote("It is expected to be present, but it isn't.") +
                    "\n {4}actual: 'OptionalInt\\.empty'" +
                    "\n {4}actual\\.value: '.+'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalInt.empty()).isPresent()))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isAbsent'")
    class IsAbsent {
        @Test
        @DisplayName("passes, when actual doesn't have content")
        void test0() {
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalInt.empty())
                    .isAbsent());
        }

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE})
        @DisplayName("throws exception, when actual has content")
        void test1(int value) {
            String message = Pattern.quote("It is expected to be absent, but it isn't.") +
                    "\n {4}actual: 'OptionalInt\\[.+]'" +
                    "\n {4}actual\\.value: '.+'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalInt.of(value)).isAbsent()))
                    .withMessageMatching(message);
        }
    }

}

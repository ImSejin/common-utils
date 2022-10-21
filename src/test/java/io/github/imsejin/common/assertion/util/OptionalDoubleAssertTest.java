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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.ContainerAssertable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.OptionalDouble;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("OptionalDoubleAssert")
class OptionalDoubleAssertTest {

    @Nested
    @DisplayName("method 'contains'")
    class Contains {
        @ParameterizedTest
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("passes, when actual contains the given content")
        void test0(Double value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalDouble.of(value))
                    .contains(value));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("throws exception, when actual doesn't contain the given content")
        void test1(Double expected) {
            String message = Pattern.quote(ContainerAssertable.DEFAULT_DESCRIPTION_CONTAINS) +
                    "\n {4}actual: 'OptionalDouble(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalDouble.empty()).contains(expected)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalDouble.of(0.1)).contains(expected)))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotContain'")
    class DoesNotContain {
        @ParameterizedTest
        @NullSource
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("passes, when actual doesn't contain the given content")
        void test0(Double expected) {
            assertThatNoException().isThrownBy((() -> Asserts.that(OptionalDouble.empty())
                    .doesNotContain(expected)));
        }

        @ParameterizedTest
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("throws exception, when actual contains the given content")
        void test1(Double value) {
            String message = Pattern.quote(ContainerAssertable.DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN) +
                    "\n {4}actual: 'OptionalDouble(\\.empty|\\[.+])'" +
                    "\n {4}actual\\.value: '.+'" +
                    "\n {4}expected: '.*'";

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Asserts.that(OptionalDouble.of(value)).doesNotContain(value))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isPresent'")
    class IsPresent {
        @ParameterizedTest
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("passes, when actual has content")
        void test0(Double value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalDouble.of(value))
                    .isPresent());
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have content")
        void test1() {
            String message = Pattern.quote("It is expected to be present, but it isn't.") +
                    "\n {4}actual: 'OptionalDouble\\.empty'" +
                    "\n {4}actual\\.value: '.+'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalDouble.empty()).isPresent()))
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
            assertThatNoException().isThrownBy(() -> Asserts.that(OptionalDouble.empty())
                    .isAbsent());
        }

        @ParameterizedTest
        @ValueSource(doubles = {Double.MIN_VALUE, -3.14, 0.0, 3.14, Double.MAX_VALUE})
        @DisplayName("throws exception, when actual has content")
        void test1(Double value) {
            String message = Pattern.quote("It is expected to be absent, but it isn't.") +
                    "\n {4}actual: 'OptionalDouble\\[.+]'" +
                    "\n {4}actual\\.value: '.+'";

            assertThatIllegalArgumentException()
                    .isThrownBy((() -> Asserts.that(OptionalDouble.of(value)).isAbsent()))
                    .withMessageMatching(message);
        }
    }

}

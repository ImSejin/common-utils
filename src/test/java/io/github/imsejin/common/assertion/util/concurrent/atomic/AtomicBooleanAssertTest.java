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

package io.github.imsejin.common.assertion.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.HolderAssertable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AtomicBooleanAssert")
class AtomicBooleanAssertTest {

    @Nested
    @DisplayName("method 'hasValue'")
    class HasValue {
        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("passes, when actual has the given value")
        void test0(boolean value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicBoolean(value))
                    .hasValue(value));
        }

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("throws exception, when actual doesn't have the given value")
        void test1(boolean value) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE) +
                    "\n {4}actual: '(true|false)'" +
                    "\n {4}expected: '(null|true|false)'";

            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicBoolean(value))
                            .hasValue(null)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicBoolean(value))
                            .hasValue(!value)))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveValue'")
    class DoesNotHaveValue {
        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("passes, when actual doesn't have the given value")
        void test0(boolean value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicBoolean(value))
                    .doesNotHaveValue(null));
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicBoolean(value))
                    .doesNotHaveValue(!value));
        }

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        @DisplayName("throws exception, when actual has the given value")
        void test1(boolean value) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE) +
                    "\n {4}actual: '(true|false)'" +
                    "\n {4}expected: '(true|false)'";

            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicBoolean(value))
                            .doesNotHaveValue(value)))
                    .withMessageMatching(message);
        }
    }

}

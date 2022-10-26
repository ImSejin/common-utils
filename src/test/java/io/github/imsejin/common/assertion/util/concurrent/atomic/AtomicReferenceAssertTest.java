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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.HolderAssertable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("AtomicReferenceAssert")
class AtomicReferenceAssertTest {

    @Nested
    @DisplayName("method 'hasValue'")
    class HasValue {
        @Test
        @DisplayName("passes, when actual has the given value")
        void test0() {
            // given
            AtomicReference<String> actual = new AtomicReference<>("alpha");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .hasValue(actual.get()));
        }

        @Test
        @DisplayName("throws exception, when actual doesn't have the given value")
        void test1() {
            // given
            AtomicReference<String> actual = new AtomicReference<>("alpha");

            // expect
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE) +
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.+'";
            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(actual)
                    .hasValue(actual.getAndSet("beta"))
                    .hasValue(actual.get())))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveValue'")
    class DoesNotHaveValue {
        @Test
        @DisplayName("passes, when actual doesn't have the given value")
        void test0() {
            // given
            AtomicReference<String> actual = new AtomicReference<>("alpha");

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .doesNotHaveValue(actual.getAndUpdate(val -> val.toUpperCase(Locale.US))));
        }

        @Test
        @DisplayName("throws exception, when actual has the given value")
        void test1() {
            // given
            AtomicReference<String> actual = new AtomicReference<>("alpha");

            // expect
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE) +
                    "\n {4}actual: '.+'" +
                    "\n {4}expected: '.+'";
            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(actual)
                    .doesNotHaveValue(actual.updateAndGet(val -> val.toUpperCase(Locale.US)))))
                    .withMessageMatching(message);
        }
    }

}

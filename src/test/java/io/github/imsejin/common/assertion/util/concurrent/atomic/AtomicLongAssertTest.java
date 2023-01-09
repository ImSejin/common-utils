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

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.composition.AmountComparisonAssertable;
import io.github.imsejin.common.assertion.composition.HolderAssertable;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AtomicLongAssert")
class AtomicLongAssertTest {

    @Nested
    @DisplayName("method 'isEqualTo'")
    class IsEqualTo {
        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("passes, when actual is equal to other")
        void test0(long value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicLong(value))
                    .isEqualTo(new AtomicLong(value)));
        }

        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("throws exception, when actual is not equal to other")
        void test1(long value) {
            // given
            AtomicLong actual = new AtomicLong(value);
            actual.lazySet(value + 1);

            // expect
            String message = Pattern.quote(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_EQUAL_TO) +
                    "\n {4}actual: '-?[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";
            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(actual)
                            .isEqualTo(new AtomicLong(value))))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'isNotEqualTo'")
    class IsNotEqualTo {
        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("passes, when actual is not equal to other")
        void test0(long value) {
            // given
            AtomicLong actual = new AtomicLong(value);
            actual.lazySet(value - 1);

            // expect
            assertThatNoException().isThrownBy(() -> Asserts.that(actual)
                    .isNotEqualTo(new AtomicLong(value)));
        }

        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("throws exception, when actual is equal to other")
        void test1(long value) {
            String message = Pattern.quote(AmountComparisonAssertable.DEFAULT_DESCRIPTION_IS_NOT_EQUAL_TO) +
                    "\n {4}actual: '-?[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicLong(value))
                            .isNotEqualTo(new AtomicLong(value))))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'hasValue'")
    class HasValue {
        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("passes, when actual has the given value")
        void test0(long value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicLong(value))
                    .hasValue(value));
        }

        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("throws exception, when actual doesn't have the given value")
        void test1(long value) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_HAS_VALUE) +
                    "\n {4}actual: '-?[0-9]+'" +
                    "\n {4}expected: '(null|-?[0-9]+)'";

            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicLong(value))
                            .hasValue(null)))
                    .withMessageMatching(message);
            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicLong(value))
                            .hasValue(value + 1)))
                    .withMessageMatching(message);
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Nested
    @DisplayName("method 'doesNotHaveValue'")
    class DoesNotHaveValue {
        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("passes, when actual doesn't have the given value")
        void test0(long value) {
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicLong(value))
                    .doesNotHaveValue(null));
            assertThatNoException().isThrownBy(() -> Asserts.that(new AtomicLong(value))
                    .doesNotHaveValue(value - 1));
        }

        @ParameterizedTest
        @ValueSource(longs = {
                Integer.MIN_VALUE, Short.MIN_VALUE, Byte.MIN_VALUE,
                -32, -8, -2, -1, 0, 1, 2, 8, 32,
                Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE,
        })
        @DisplayName("throws exception, when actual has the given value")
        void test1(long value) {
            String message = Pattern.quote(HolderAssertable.DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE) +
                    "\n {4}actual: '-?[0-9]+'" +
                    "\n {4}expected: '-?[0-9]+'";

            assertThatIllegalArgumentException().isThrownBy((() -> Asserts.that(new AtomicLong(value))
                            .doesNotHaveValue(value)))
                    .withMessageMatching(message);
        }
    }

}

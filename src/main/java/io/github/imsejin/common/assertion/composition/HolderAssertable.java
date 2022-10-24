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

package io.github.imsejin.common.assertion.composition;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Composition of assertion for holder.
 *
 * @param <SELF>  assertion class
 * @param <VALUE> holding value
 * @see Optional
 * @see OptionalInt
 * @see OptionalLong
 * @see OptionalDouble
 */
public interface HolderAssertable<
        SELF extends HolderAssertable<SELF, VALUE>,
        VALUE> {

    String DEFAULT_DESCRIPTION_HAS_VALUE = "It is expected to have the given value, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_VALUE = "It is expected not to have the given value, but it is.";

    /**
     * Asserts that actual value has expected value as a content.
     *
     * <p> If actual value is empty, the assertion always fails.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).contains(1);
     *     Asserts.that(['a', 'b', 'c', 'd']).contains('b');
     *
     *     // Assertion will fail.
     *     Asserts.that([]).contains(['a']);
     *     Asserts.that([1, 2, 3]).contains(0);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasValue(VALUE expected);

    /**
     * Asserts that actual value doesn't contain expected value as a content.
     *
     * <p> If actual value is empty, the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).doesNotContain(['a']);
     *     Asserts.that([1, 2, 3]).doesNotContain(0);
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).doesNotContain(1);
     *     Asserts.that(['a', 'b', 'c', 'd']).doesNotContain('b');
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF doesNotHaveValue(VALUE expected);

}

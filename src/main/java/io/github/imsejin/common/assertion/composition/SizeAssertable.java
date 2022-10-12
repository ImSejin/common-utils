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

/**
 * Composition of assertion for size.
 *
 * @param <SELF>   assertion class
 * @param <ACTUAL> something that has size
 */
public interface SizeAssertable<
        SELF extends SizeAssertable<SELF, ACTUAL>,
        ACTUAL> {

    String DEFAULT_DESCRIPTION_IS_EMPTY = "It is expected to be empty, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_IS_NOT_EMPTY = "It is expected not to be empty, but it is. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_HAS_SIZE = "It is expected to have the given size, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE = "It is expected not to have the given size, but it is. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS = "They are expected to have the same size, but they aren't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS = "They are expected not to have the same size, but they are. (expected: '{0}', actual: '{1}')";

    /**
     * Asserts that actual value is empty.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).isEmpty();
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).isEmpty();
     * }</pre>
     *
     * @return this class
     */
    SELF isEmpty();

    /**
     * Asserts that actual value is not empty.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).isNotEmpty();
     *
     *     // Assertion will fail.
     *     Asserts.that([]).isNotEmpty();
     * }</pre>
     *
     * @return this class
     */
    SELF isNotEmpty();

    /**
     * Asserts that actual value has as many elements as expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).hasSize(0);
     *     Asserts.that([1, 2, 3, 4, 5]).hasSize(5);
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).hasSize(3);
     *     Asserts.that(['a', 'b', 'c', 'd']).hasSize(1);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSize(long expected);

    /**
     * Asserts that actual value doesn't have as many elements as expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).doesNotHaveSize(3);
     *     Asserts.that(['a', 'b', 'c', 'd']).doesNotHaveSize(1);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).doesNotHaveSize(0);
     *     Asserts.that([1, 2, 3, 4, 5]).doesNotHaveSize(5);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF doesNotHaveSize(long expected);

    /**
     * Asserts that actual value and expected value have the same size.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).hasSameSizeAs([]);
     *     Asserts.that([1, 2, 3]).hasSameSizeAs(['a' 'b', 'c']);
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).hasSameSizeAs([1, 2, 3]);
     *     Asserts.that(['a', 'b', 'c', 'd']).hasSameSizeAs(['a']);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSameSizeAs(ACTUAL expected);

    /**
     * Asserts that actual value and expected value don't have the same size.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).doesNotHaveSameSizeAs([1, 2, 3]);
     *     Asserts.that(['a', 'b', 'c', 'd']).doesNotHaveSameSizeAs(['a']);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).doesNotHaveSameSizeAs([]);
     *     Asserts.that([1, 2, 3]).doesNotHaveSameSizeAs(['a' 'b', 'c']);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF doesNotHaveSameSizeAs(ACTUAL expected);

}

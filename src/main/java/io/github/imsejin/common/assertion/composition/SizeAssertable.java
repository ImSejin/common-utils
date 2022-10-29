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

    String DEFAULT_DESCRIPTION_IS_EMPTY = "It is expected to be empty, but it isn't.";
    String DEFAULT_DESCRIPTION_IS_NOT_EMPTY = "It is expected not to be empty, but it is.";
    String DEFAULT_DESCRIPTION_HAS_SIZE = "It is expected to have the given size, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE = "It is expected not to have the given size, but it is.";
    String DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS = "They are expected to have the same size, but they aren't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS = "They are expected not to have the same size, but they are.";
    String DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN = "It is expected to have size greater than the given one, but it isn't.";
    String DEFAULT_DESCRIPTION_HAS_SIZE_GREATER_THAN_OR_EQUAL_TO = "It is expected to have size greater than or same as the given one, but it isn't.";
    String DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN = "It is expected to have size less than the given one, but it isn't.";
    String DEFAULT_DESCRIPTION_HAS_SIZE_LESS_THAN_OR_EQUAL_TO = "It is expected to have size less than or same as the given one, but it isn't.";

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

    /**
     * Asserts that actual value has greater size than expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(['a']).hasSizeGreaterThan(0);
     *     Asserts.that([1, 2, 3, 4, 5]).hasSizeGreaterThan(4);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).hasSizeGreaterThan(0);
     *     Asserts.that(['a', 'b', 'c']).hasSizeGreaterThan(4);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSizeGreaterThan(long expected);

    /**
     * Asserts that actual value has greater or same size than expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(['a']).hasSizeGreaterThanOrEqualTo(1);
     *     Asserts.that([1, 2, 3, 4, 5]).hasSizeGreaterThanOrEqualTo(4);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).hasSizeGreaterThanOrEqualTo(1);
     *     Asserts.that(['a', 'b', 'c']).hasSizeGreaterThanOrEqualTo(4);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSizeGreaterThanOrEqualTo(long expected);

    /**
     * Asserts that actual value has less size than expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(['a']).hasSizeLessThan(2);
     *     Asserts.that([1, 2, 3, 4, 5]).hasSizeLessThan(5);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).hasSizeLessThan(0);
     *     Asserts.that(['a', 'b', 'c']).hasSizeLessThan(2);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSizeLessThan(long expected);

    /**
     * Asserts that actual value has less or same size than expected.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).hasSizeLessThanOrEqualTo(0);
     *     Asserts.that([1, 2, 3, 4, 5]).hasSizeLessThanOrEqualTo(6);
     *
     *     // Assertion will fail.
     *     Asserts.that([1]).hasSizeLessThanOrEqualTo(0);
     *     Asserts.that(['a', 'b', 'c']).hasSizeLessThanOrEqualTo(2);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF hasSizeLessThanOrEqualTo(long expected);

}

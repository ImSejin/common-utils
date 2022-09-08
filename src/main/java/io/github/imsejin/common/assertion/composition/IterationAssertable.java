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

import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.function.Predicate;

/**
 * Composition of assertion for iteration.
 *
 * @param <SELF>    assertion class
 * @param <ACTUAL>  type that can iterate
 * @param <ELEMENT> element that {@link ACTUAL} has
 * @see java.lang.reflect.Array
 * @see Iterable
 */
public interface IterationAssertable<SELF extends ObjectAssert<SELF, ACTUAL>, ACTUAL, ELEMENT> {

    String DEFAULT_DESCRIPTION_IS_EMPTY = "It is expected to be empty, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_IS_NOT_EMPTY = "It is expected not to be empty, but it is. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_HAS_SIZE = "It is expected to have the given size, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SIZE = "It is expected not to have the given size, but it is. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_HAS_SAME_SIZE_AS = "They are expected to have the same size, but they aren't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_SAME_SIZE_AS = "They are expected not to have the same size, but they are. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_NULL = "It is expected to contain null, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL = "It is expected not to contain null, but it is. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_CONTAINS = "It is expected to contain the given element, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN = "It is expected not to contain the given element, but it is. (expected: '{0}', actual: '{1}', unexpected: '{2}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ANY = "It is expected to contain at least one of the given element(s), but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ALL = "It is expected to contain all the given elements, but it isn't. (expected: '{0}', actual: '{1}', missing: '{2}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL = "It is expected not to contain all the given elements, but it is. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_MISSING = "It is expected to contain only the given element(s), but it doesn't contain some element(s). (expected: '{0}', actual: '{1}', missing: '{2}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_UNEXPECTED = "It is expected to contain only the given element(s), but it contains unexpected element(s). (expected: '{0}', actual: '{1}', unexpected: '{2}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS = "It is expected to contain only null elements, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_DUPLICATES = "It is expected not to have duplicated elements, but it is. (actual: '{0}', duplicated: '{1}')";
    String DEFAULT_DESCRIPTION_ANY_MATCH = "It is expected to match the given condition with its any elements, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_ALL_MATCH = "It is expected to match the given condition with its all elements, but it isn't. (actual: '{0}', unexpected: '{1}')";
    String DEFAULT_DESCRIPTION_NONE_MATCH = "It is expected not to match the given condition with its all elements, but it is. (actual: '{0}', unexpected: '{1}')";

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
    SELF hasSize(int expected);

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
    SELF doesNotHaveSize(int expected);

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
     * Asserts that actual value contains expected value as a element.
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
    SELF contains(ELEMENT expected);

    /**
     * Asserts that actual value doesn't contain expected value as a element.
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
    SELF doesNotContain(ELEMENT expected);

    /**
     * Asserts that actual value contains {@code null} as a element.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([null]).containsNull();
     *     Asserts.that([1, 2, null, 4, 5]).containsNull();
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsNull();
     *     Asserts.that([1, 2, 3]).containsNull();
     * }</pre>
     *
     * @return this class
     */
    SELF containsNull();

    /**
     * Asserts that actual value doesn't contain {@code null} as a element.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).doesNotContainNull();
     *     Asserts.that([1, 2, 3]).doesNotContainNull();
     *
     *     // Assertion will fail.
     *     Asserts.that([null]).doesNotContainNull();
     *     Asserts.that([1, 2, null, 4, 5]).doesNotContainNull();
     * }</pre>
     *
     * @return this class
     */
    SELF doesNotContainNull();

    /**
     * Asserts that actual value contains at least 1 element of expected values.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).containsAny();
     *     Asserts.that([1, 2, 3]).containsAny(2, 3, 4);
     *     Asserts.that(['a', 'b', 'c', 'd']).containsAny('b');
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsAny('a');
     *     Asserts.that([1, 2, 3, 4, 5]).containsAny(6, 7);
     * }</pre>
     *
     * @param expected expected values
     * @return this class
     */
    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF containsAny(ELEMENT... expected);

    /**
     * Asserts that actual value contains all the element of expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([0]).containsAll([]);
     *     Asserts.that([1, 2, 3]).containsAll([2, 3]);
     *     Asserts.that(['a', 'b', 'c', 'd']).containsAll(['a', 'b', 'd']);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsAll(['a']);
     *     Asserts.that([1, 2, 3, 4, 5]).containsAll([0, 1, 2]);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     * @see #containsOnly(Object[])
     */
    SELF containsAll(ACTUAL expected);

    /**
     * Asserts that actual value doesn't contain all the element of expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).doesNotContainAll([1, 2]);
     *     Asserts.that([1, 2]).doesNotContainAll([]);
     *     Asserts.that(['a', 'b', 'c']).doesNotContainAll(['d', 'e']);
     *
     *     // Assertion will fail.
     *     Asserts.that(['a', 'b']).doesNotContainAll(['a']);
     *     Asserts.that([1, 2, 3, 4, 5]).doesNotContainAll([5, 6, 7]);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    SELF doesNotContainAll(ACTUAL expected);

    /**
     * Asserts that actual value contains only expected values.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([0]).containsOnly([]);
     *     Asserts.that([1, 2, 3]).containsOnly([2, 3, 1]);
     *     Asserts.that(['a', 'b', 'c', 'd']).containsOnly(['a', 'b', 'd', 'c']);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsOnly(['a']);
     *     Asserts.that([1, 2, 3, 4, 5]).containsOnly([0, 1, 2]);
     * }</pre>
     *
     * @param expected expected values
     * @return this class
     * @see #containsAll(Object)
     */
    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF containsOnly(ELEMENT... expected);

    /**
     * Asserts that actual value contains only {@code null}.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([null]).containsOnlyNulls();
     *     Asserts.that([null, null]).containsOnlyNulls();
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsOnlyNulls();
     *     Asserts.that([1, 2, null, 4]).containsOnlyNulls();
     * }</pre>
     *
     * @return this class
     */
    SELF containsOnlyNulls();

    /**
     * Asserts that actual value doesn't contain duplicated elements.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).doesNotHaveDuplicates();
     *     Asserts.that([1]).doesNotHaveDuplicates();
     *     Asserts.that(['a', 'b', 'c', null]).doesNotHaveDuplicates();
     *
     *     // Assertion will fail.
     *     Asserts.that([null, 2, null, 4]).doesNotHaveDuplicates();
     *     Asserts.that(['a', 'b', 'a', 'd']).doesNotHaveDuplicates();
     * }</pre>
     *
     * @return this class
     */
    SELF doesNotHaveDuplicates();

    /**
     * Asserts that actual value has at least 1 element which satisfies expected condition.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3]).anyMatch(it -> it == 1);
     *     Asserts.that(['a', 'b', 'c', null]).anyMatch(Objects::isNull);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).anyMatch(it -> true);
     *     Asserts.that([1, 2, 3]).anyMatch(it -> it <= 0);
     *     Asserts.that(['a', 'b', 'c', 'd']).anyMatch(Character::isUpperCase);
     * }</pre>
     *
     * @param expected expected condition
     * @return this class
     */
    SELF anyMatch(Predicate<ELEMENT> expected);

    /**
     * Asserts that actual value has all the elements which satisfy expected condition.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3]).allMatch(it -> it > 0);
     *     Asserts.that(['a', 'b', 'c', 'd']).allMatch(Character::isLowerCase);
     *
     *     // Assertion will fail.
     *     Asserts.that([]).allMatch(it -> true);
     *     Asserts.that([1, 2, 3]).allMatch(it -> it <= 0);
     *     Asserts.that(['A', 'B', 'C', 'd']).allMatch(Character::isUpperCase);
     * }</pre>
     *
     * @param expected expected condition
     * @return this class
     */
    SELF allMatch(Predicate<ELEMENT> expected);

    /**
     * Asserts that actual value has no element which satisfies expected condition.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).noneMatch(it -> true);
     *     Asserts.that([1, 2, 3]).noneMatch(it -> it <= 0);
     *     Asserts.that(['a', 'b', 'c', 'd']).noneMatch(Character::isUpperCase);
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3]).noneMatch(it -> it == 1);
     *     Asserts.that(['a', 'b', 'c', null]).noneMatch(Objects::isNull);
     * }</pre>
     *
     * @param expected expected condition
     * @return this class
     */
    SELF noneMatch(Predicate<ELEMENT> expected);

}

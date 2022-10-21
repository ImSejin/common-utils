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

import java.util.function.Predicate;

/**
 * Composition of assertion for iteration.
 *
 * @param <SELF>    assertion class
 * @param <ACTUAL>  iterable type
 * @param <ELEMENT> element of {@link ACTUAL}
 * @see java.lang.reflect.Array
 * @see Iterable
 */
public interface IterationAssertable<
        SELF extends IterationAssertable<SELF, ACTUAL, ELEMENT>,
        ACTUAL,
        ELEMENT>
        extends SizeAssertable<SELF, ACTUAL> {

    String DEFAULT_DESCRIPTION_CONTAINS_NULL = "It is expected to contain null, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL = "It is expected not to contain null, but it is.";
    String DEFAULT_DESCRIPTION_CONTAINS_ANY = "It is expected to contain at least one of the given element(s), but it isn't.";
    String DEFAULT_DESCRIPTION_CONTAINS_ALL = "It is expected to contain all the given elements, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL = "It is expected not to contain all the given elements, but it is.";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_MISSING = "It is expected to contain only the given element(s), but it doesn't contain some element(s).";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_UNEXPECTED = "It is expected to contain only the given element(s), but it contains unexpected element(s).";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS = "It is expected to contain only null elements, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_HAVE_DUPLICATES = "It is expected not to have duplicated elements, but it is.";
    String DEFAULT_DESCRIPTION_ANY_MATCH = "It is expected to match the given condition with its any elements, but it isn't.";
    String DEFAULT_DESCRIPTION_ALL_MATCH = "It is expected to match the given condition with its all elements, but it isn't.";
    String DEFAULT_DESCRIPTION_NONE_MATCH = "It is expected not to match the given condition with its all elements, but it is.";

    /**
     * Asserts that actual value contains {@code null} as a element.
     *
     * <p> If actual value is empty, the assertion always fails.
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
     * <p> If actual value is empty, the assertion always passes.
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
     * <p> If actual value is empty and expected value is empty, the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).containsAny();
     *     Asserts.that([1, 2, 3]).containsAny(2, 3, 4);
     *     Asserts.that(['a', 'b', 'c', 'd']).containsAny('b');
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsAny('a');
     *     Asserts.that([1, 2, 3]).containsAny();
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
     * <p> If actual value is empty and expected value is empty, the assertion always passes.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([]).containsOnly();
     *     Asserts.that([1, 2, 3]).containsOnly(2, 3, 1);
     *     Asserts.that(['a', 'b', 'c', 'd']).containsOnly('a', 'b', 'd', 'c');
     *
     *     // Assertion will fail.
     *     Asserts.that([]).containsOnly('a');
     *     Asserts.that([1, 2, 3]).containsOnly();
     *     Asserts.that([1, 2, 3, 4, 5]).containsOnly(0, 1, 2);
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
     * <p> If actual value is empty, the assertion always fails.
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
     * <p> If actual value is empty, the assertion always fails.
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
     * <p> If actual value is empty, the assertion always fails.
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
     * <p> If actual value is empty, the assertion always passes.
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

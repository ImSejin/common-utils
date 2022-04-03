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
    String DEFAULT_DESCRIPTION_HAS_ELEMENT = "It is expected to have element, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_CONTAINS_NULL = "It is expected to contain null, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_NULL = "It is expected not to contain null, but it isn't. (actual: '{0}')";
    String DEFAULT_DESCRIPTION_CONTAINS = "It is expected to contain the given element, but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN = "It is expected not to contain the given element, but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ANY = "It is expected to contain at least one of the given element(s), but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ALL = "It is expected to contain all the given elements, but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN_ALL = "It is expected not to contain all the given elements, but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY = "It is expected to contain only the given element(s), but it doesn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_CONTAINS_ONLY_NULLS = "It is expected to contain only null elements, but it doesn't. (expected: '{0}', actual: '{1}')";

    SELF isEmpty();

    SELF hasElement();

    SELF containsNull();

    SELF doesNotContainNull();

    SELF contains(ELEMENT expected);

    SELF doesNotContain(ELEMENT expected);

    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value to its elements.
    @SuppressWarnings("unchecked")
    SELF containsAny(ELEMENT... expected);

    SELF containsAll(ACTUAL expected);

    SELF doesNotContainAll(ACTUAL expected);

    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value to its elements.
    @SuppressWarnings("unchecked")
    SELF containsOnly(ELEMENT... expected);

    SELF containsOnlyNulls();

}

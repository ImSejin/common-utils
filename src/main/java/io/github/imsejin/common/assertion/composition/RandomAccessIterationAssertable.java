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

import java.util.List;

/**
 * Composition of assertion for iteration randomly accessible.
 *
 * @param <SELF>    assertion class
 * @param <ELEMENT> element
 * @see java.lang.reflect.Array
 * @see List
 */
public interface RandomAccessIterationAssertable<
        SELF extends RandomAccessIterationAssertable<SELF, ELEMENT>,
        ELEMENT> {

    String DEFAULT_DESCRIPTION_STARTS_WITH_OVER_SIZE = "It is expected to start with the given element(s), but it has fewer element(s) than that.";
    String DEFAULT_DESCRIPTION_STARTS_WITH_UNEXPECTED_ELEMENT = "It is expected to start with the given element(s), but it contains unexpected element(s).";
    String DEFAULT_DESCRIPTION_ENDS_WITH_OVER_SIZE = "It is expected to end with the given element(s), but it has fewer element(s) than that.";
    String DEFAULT_DESCRIPTION_ENDS_WITH_UNEXPECTED_ELEMENT = "It is expected to end with the given element(s), but it contains unexpected element(s).";

    /**
     * Asserts that actual value has elements started with expected values.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).startsWith(1, 2, 3);
     *     Asserts.that(['a', 'b', 'c', 'd']).startsWith('a', 'b', 'c', 'd');
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).startsWith(0, 1, 2);
     *     Asserts.that(['a', 'b', 'c', 'd']).startsWith('a', 'b', 'd', 'c');
     * }</pre>
     *
     * @param expected expected values
     * @return this class
     */
    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF startsWith(ELEMENT... expected);

    /**
     * Asserts that actual value has elements ended with expected values.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that([1, 2, 3, 4, 5]).endsWith(3, 4, 5);
     *     Asserts.that(['a', 'b', 'c', 'd']).endsWith('a', 'b', 'c', 'd');
     *
     *     // Assertion will fail.
     *     Asserts.that([1, 2, 3, 4, 5]).endsWith(4, 5, 6);
     *     Asserts.that(['a', 'b', 'c', 'd']).endsWith('b', 'a', 'c', 'd');
     * }</pre>
     *
     * @param expected expected values
     * @return this class
     */
    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF endsWith(ELEMENT... expected);

    // -------------------------------------------------------------------------------------------------

    ObjectAssert<?, ELEMENT> asFirstElement();

    ObjectAssert<?, ELEMENT> asLastElement();

    ObjectAssert<?, ELEMENT> asElement(int index);

}

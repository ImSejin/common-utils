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
 * Composition of assertion for enumeration.
 *
 * @param <SELF>    assertion class
 * @param <ACTUAL>  enumerable type
 * @param <ELEMENT> element of {@link ACTUAL}
 * @see java.lang.reflect.Array
 * @see Iterable
 * @see CharSequence
 */
public interface EnumerationAssertable<
        SELF extends EnumerationAssertable<SELF, ACTUAL, ELEMENT>,
        ACTUAL,
        ELEMENT>
        extends SizeAssertable<SELF, ACTUAL> {

    String DEFAULT_DESCRIPTION_CONTAINS = "It is expected to contain the given element, but it isn't. (expected: '{0}', actual: '{1}')";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN = "It is expected not to contain the given element, but it is. (expected: '{0}', actual: '{1}', unexpected: '{2}')";

    /**
     * Asserts that actual value contains expected value as a element.
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
    SELF contains(ELEMENT expected);

    /**
     * Asserts that actual value doesn't contain expected value as a element.
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
    SELF doesNotContain(ELEMENT expected);

}

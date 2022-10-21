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

import java.lang.reflect.Array;
import java.util.Optional;

/**
 * Composition of assertion for container.
 *
 * @param <SELF>    assertion class
 * @param <CONTENT> content in the container
 * @see Array
 * @see Iterable
 * @see CharSequence
 * @see Optional
 */
public interface ContainerAssertable<
        SELF extends ContainerAssertable<SELF, CONTENT>,
        CONTENT> {

    String DEFAULT_DESCRIPTION_CONTAINS = "It is expected to contain the given content, but it isn't.";
    String DEFAULT_DESCRIPTION_DOES_NOT_CONTAIN = "It is expected not to contain the given content, but it is.";

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
    SELF contains(CONTENT expected);

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
    SELF doesNotContain(CONTENT expected);

}

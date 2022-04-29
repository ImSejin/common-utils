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
 * @param <ACTUAL>  type that can iterate and randomly accessible
 * @param <ELEMENT> element that {@link ACTUAL} has
 * @see java.lang.reflect.Array
 * @see List
 */
public interface RandomAccessIterationAssertable<SELF extends ObjectAssert<SELF, ACTUAL>, ACTUAL, ELEMENT> {

    String DEFAULT_DESCRIPTION_STARTS_WITH = "It is expected to contain at least one of the given element(s), but it isn't. (expected: '{0}', actual: '{1}')";

    String DEFAULT_DESCRIPTION_ENDS_WITH = "It is expected to contain at least one of the given element(s), but it isn't. (expected: '{0}', actual: '{1}')";

    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF startsWith(ELEMENT... expected);

    // It is safe for heap pollution from parameterized vararg type.
    // This doesn't return the parameter and doesn't set any value as its element.
    @SuppressWarnings("unchecked")
    SELF endsWith(ELEMENT... expected);

}

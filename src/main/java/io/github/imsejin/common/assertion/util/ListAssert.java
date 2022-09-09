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

package io.github.imsejin.common.assertion.util;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.RandomAccessIterationAssertable;
import io.github.imsejin.common.util.ArrayUtils;

import java.util.List;
import java.util.Objects;

/**
 * Assertion for {@link List}
 *
 * @param <SELF>    this class
 * @param <ACTUAL>  type of list
 * @param <ELEMENT> type of element in list
 */
public class ListAssert<
        SELF extends ListAssert<SELF, ACTUAL, ELEMENT>,
        ACTUAL extends List<? extends ELEMENT>,
        ELEMENT>
        extends CollectionAssert<SELF, ACTUAL, ELEMENT>
        implements RandomAccessIterationAssertable<SELF, ACTUAL, ELEMENT> {

    public ListAssert(ACTUAL actual) {
        super(actual);
    }

    protected ListAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor, actual);
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected values
     * @return this class
     */
    @Override
    @SafeVarargs
    public final SELF startsWith(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        if (actual.size() < expected.length) {
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH, expected, actual);
            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual.get(i);
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH, expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * {@inheritDoc}
     *
     * @param expected expected values
     * @return this class
     */
    @Override
    @SafeVarargs
    public final SELF endsWith(ELEMENT... expected) {
        if (ArrayUtils.isNullOrEmpty(expected)) {
            return self;
        }

        if (actual.size() < expected.length) {
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH, expected, actual);
            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual.get(actual.size() - expected.length + i);
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH, expected, actual);
            throw getException();
        }

        return null;
    }

}

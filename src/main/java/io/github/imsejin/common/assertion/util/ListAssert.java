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

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Objects;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.RandomAccessIterationAssertable;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.ArrayUtils;

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
        implements RandomAccessIterationAssertable<SELF, ELEMENT> {

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
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_OVER_SIZE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("expected.size", expected.length));

            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual.get(i);
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_STARTS_WITH_UNEXPECTED_ELEMENT);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("unexpected", element));

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
            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_OVER_SIZE);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("actual.size", actual.size()),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("expected.size", expected.length));

            throw getException();
        }

        for (int i = 0; i < expected.length; i++) {
            ELEMENT element = actual.get(actual.size() - expected.length + i);
            ELEMENT item = expected[i];
            if (Objects.deepEquals(element, item)) {
                continue;
            }

            setDefaultDescription(RandomAccessIterationAssertable.DEFAULT_DESCRIPTION_ENDS_WITH_UNEXPECTED_ELEMENT);
            setDescriptionVariables(
                    new SimpleEntry<>("actual", actual),
                    new SimpleEntry<>("expected", expected),
                    new SimpleEntry<>("unexpected", element));

            throw getException();
        }

        return null;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public ObjectAssert<?, ELEMENT> asFirstElement() {
        return asElement(0);
    }

    @Override
    public ObjectAssert<?, ELEMENT> asLastElement() {
        return asElement(actual.size() - 1);
    }

    @Override
    public ObjectAssert<?, ELEMENT> asElement(int index) {
        class ObjectAssertImpl extends ObjectAssert<ObjectAssertImpl, ELEMENT> {
            ObjectAssertImpl(Descriptor<?> descriptor, ELEMENT element) {
                super(descriptor, element);
            }
        }

        Asserts.that(index)
                .isNotNull()
                .isZeroOrPositive()
                .isLessThan(actual.size());

        return new ObjectAssertImpl(this, actual.get(index));
    }

}

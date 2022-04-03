/*
 * Copyright 2021 Sejin Im
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

package io.github.imsejin.common.assertion.collection;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.composition.IterationAssertable;
import io.github.imsejin.common.assertion.lang.ArrayAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;
import io.github.imsejin.common.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public abstract class AbstractCollectionAssert<
        SELF extends AbstractCollectionAssert<SELF, ACTUAL, ELEMENT>,
        ACTUAL extends Collection<? extends ELEMENT>,
        ELEMENT>
        extends ObjectAssert<SELF, ACTUAL>
        implements IterationAssertable<SELF, ACTUAL, ELEMENT> {

    protected AbstractCollectionAssert(ACTUAL target) {
        super(target);
    }

    @Override
    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription("It is expected to be empty, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF hasElement() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to have element, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF doesNotContainNull() {
        for (ELEMENT element : actual) {
            if (element != null) continue;

            setDefaultDescription("It is expected not to contain null, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF hasSizeOf(int expected) {
        if (actual.size() != expected) {
            setDefaultDescription("It is expected to be the same size, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.size());
            throw getException();
        }

        return self;
    }

    public SELF isSameSize(Collection<?> expected) {
        if (expected == null || actual.size() != expected.size()) {
            setDefaultDescription("They are expected to be the same size, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF isNotSameSize(Collection<?> expected) {
        if (expected == null || actual.size() == expected.size()) {
            setDefaultDescription("They are expected to be not the same size, but they are. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    @Override
    public SELF contains(ELEMENT expected) {
        if (!actual.contains(expected)) {
            setDefaultDescription("It is expected to contain the given element, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    @Override
    public SELF containsAny(ELEMENT... expected) {
        if (expected.length == 0) return self;

        for (Object item : expected) {
            for (Object element : actual) {
                if (Objects.deepEquals(element, item)) return self;
            }
        }

        setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ANY, Arrays.deepToString(expected), actual);
        throw getException();
    }

    @Override
    public SELF containsAll(ACTUAL expected) {
        if (CollectionUtils.isNullOrEmpty(expected)) return self;

        if (!actual.containsAll(expected)) {
            setDefaultDescription(IterationAssertable.DEFAULT_DESCRIPTION_CONTAINS_ALL, expected, actual);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public ArrayAssert<?, ELEMENT> asArray() {
        ArrayAssert<?, ELEMENT> assertion = Asserts.that((ELEMENT[]) actual.toArray());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public NumberAssert<?, Integer> asSize() {
        NumberAssert<?, Integer> assertion = Asserts.that(actual.size());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}

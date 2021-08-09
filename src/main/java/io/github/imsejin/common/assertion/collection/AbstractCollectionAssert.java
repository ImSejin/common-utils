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
import io.github.imsejin.common.assertion.array.ArrayAssert;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.assertion.primitive.NumberAssert;
import io.github.imsejin.common.util.CollectionUtils;

import java.util.Collection;

public abstract class AbstractCollectionAssert<
        SELF extends AbstractCollectionAssert<SELF, ACTUAL, T>,
        ACTUAL extends Collection<T>,
        T>
        extends AbstractObjectAssert<SELF, Collection<T>> {

    protected AbstractCollectionAssert(ACTUAL target) {
        super(target);
    }

    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription("It is expected to be empty, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF hasElement() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to have element, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF doesNotContainNull() {
        for (T element : actual) {
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

    public SELF contains(T expected) {
        if (!actual.contains(expected)) {
            setDefaultDescription("It is expected to contain the given element, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF containsAll(Collection<T> expected) {
        if (CollectionUtils.isNullOrEmpty(expected)) return self;

        if (!actual.containsAll(expected)) {
            setDefaultDescription("It is expected to contain the given collection, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ArrayAssert<?> asArray() {
        return Asserts.that(actual.toArray());
    }

    public NumberAssert<?, Integer> asSize() {
        return Asserts.that(actual.size());
    }

}

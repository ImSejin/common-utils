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

package io.github.imsejin.common.assertion.map;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.collection.AbstractCollectionAssert;
import io.github.imsejin.common.assertion.lang.NumberAssert;
import io.github.imsejin.common.assertion.lang.ObjectAssert;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapAssert<
        SELF extends AbstractMapAssert<SELF, ACTUAL, K, V>,
        ACTUAL extends Map<K, V>,
        K,
        V>
        extends ObjectAssert<SELF, ACTUAL> {

    protected AbstractMapAssert(ACTUAL actual) {
        super(actual);
    }

    public SELF isEmpty() {
        if (!actual.isEmpty()) {
            setDefaultDescription("It is expected to be empty, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF hasEntry() {
        if (actual.isEmpty()) {
            setDefaultDescription("It is expected to have entry, but it isn't. (actual: '{0}')", actual);
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

    public SELF isSameSize(Map<?, ?> expected) {
        if (expected == null || actual.size() != expected.size()) {
            setDefaultDescription("They are expected to be the same size, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF isNotSameSize(Map<?, ?> expected) {
        if (expected == null || actual.size() == expected.size()) {
            setDefaultDescription("They are expected to be not the same size, but they are. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.size(), actual.size());
            throw getException();
        }

        return self;
    }

    public SELF containsKey(K expected) {
        if (!actual.containsKey(expected)) {
            setDefaultDescription("It is expected to contain the given key, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.keySet());
            throw getException();
        }

        return self;
    }

    public SELF containsValue(V expected) {
        if (!actual.containsValue(expected)) {
            setDefaultDescription("It is expected to contain the given value, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.values());
            throw getException();
        }

        return self;
    }

    public SELF containsAllKeys(Map<? extends K, ?> expected) {
        return containsAllKeys(expected.keySet());
    }

    public SELF containsAllKeys(Collection<? extends K> expected) {
        Set<K> actualKeys = actual.keySet();

        if (!actualKeys.containsAll(expected)) {
            setDefaultDescription("It is expected to contain all the given keys, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actualKeys);
            throw getException();
        }

        return self;
    }

    public SELF containsAllValues(Map<?, ? extends V> expected) {
        return containsAllValues(expected.values());
    }

    public SELF containsAllValues(Collection<? extends V> expected) {
        Collection<V> actualValues = actual.values();

        if (!actualValues.containsAll(expected)) {
            setDefaultDescription("It is expected to contain all the given values, but it doesn't. (expected: '{0}', actual: '{1}')",
                    expected, actualValues);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public AbstractCollectionAssert<?, Collection<K>, K> asKeySet() {
        AbstractCollectionAssert<?, Collection<K>, K> assertion = Asserts.that(actual.keySet());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public AbstractCollectionAssert<?, Collection<V>, V> asValues() {
        AbstractCollectionAssert<?, Collection<V>, V> assertion = Asserts.that(actual.values());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public NumberAssert<?, Integer> asSize() {
        NumberAssert<?, Integer> assertion = Asserts.that(actual.size());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}

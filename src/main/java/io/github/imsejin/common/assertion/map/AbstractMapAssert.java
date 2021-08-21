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
import io.github.imsejin.common.assertion.collection.AbstractCollectionAssert;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.assertion.primitive.NumberAssert;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractMapAssert<
        SELF extends AbstractMapAssert<SELF, ACTUAL, K, V>,
        ACTUAL extends Map<?, ?>,
        K,
        V>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractMapAssert(ACTUAL actual) {
        super(actual);
    }

    public SELF isEmpty() {
        if (!actual.isEmpty()) throw getException();
        return self;
    }

    public SELF hasEntry() {
        if (actual.isEmpty()) throw getException();
        return self;
    }

    public SELF hasSizeOf(int expected) {
        if (actual.size() != expected) throw getException();
        return self;
    }

    public SELF isSameSize(ACTUAL expected) {
        if (expected == null || actual.size() != expected.size()) throw getException();
        return self;
    }

    public SELF isNotSameSize(ACTUAL expected) {
        if (expected != null && actual.size() == expected.size()) throw getException();
        return self;
    }

    public SELF containsKey(K key) {
        if (!actual.containsKey(key)) throw getException();
        return self;
    }

    public SELF containsValue(V value) {
        if (!actual.containsValue(value)) throw getException();
        return self;
    }

    public SELF containsAllKeys(ACTUAL expected) {
        if (!actual.keySet().containsAll(expected.keySet())) throw getException();
        return self;
    }

    public SELF containsAllKeys(Collection<K> expected) {
        if (!actual.keySet().containsAll(expected)) throw getException();
        return self;
    }

    public SELF containsAllValues(ACTUAL expected) {
        if (!actual.values().containsAll(expected.values())) throw getException();
        return self;
    }

    public SELF containsAllValues(Collection<V> expected) {
        if (!actual.values().containsAll(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public AbstractCollectionAssert<?, Collection<K>, K> asKeySet() {
        return Asserts.that((Collection<K>) actual.keySet());
    }

    @SuppressWarnings("unchecked")
    public AbstractCollectionAssert<?, Collection<V>, V> asValues() {
        return Asserts.that((Collection<V>) actual.values());
    }

    public NumberAssert<?, Integer> asSize() {
        return Asserts.that(actual.size());
    }

}

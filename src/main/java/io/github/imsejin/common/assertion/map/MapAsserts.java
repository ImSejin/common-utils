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

import io.github.imsejin.common.assertion.object.ObjectAsserts;

import java.util.Map;

@SuppressWarnings("unchecked")
public class MapAsserts<SELF extends MapAsserts<SELF, K, V>, K, V> extends ObjectAsserts<SELF> {

    private final Map<K, V> actual;

    public MapAsserts(Map<K, V> actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF isEmpty() {
        if (!this.actual.isEmpty()) throw getException();
        return (SELF) this;
    }

    public SELF hasEntry() {
        if (this.actual.isEmpty()) throw getException();
        return (SELF) this;
    }

    public SELF hasSizeOf(int expected) {
        if (this.actual.size() != expected) throw getException();
        return (SELF) this;
    }

    public SELF isSameSize(Map<?, ?> expected) {
        if (expected == null || this.actual.size() != expected.size()) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameSize(Map<?, ?> expected) {
        if (expected != null && this.actual.size() == expected.size()) throw getException();
        return (SELF) this;
    }

    public SELF containsKey(K key) {
        if (!this.actual.containsKey(key)) throw getException();
        return (SELF) this;
    }

    public SELF containsValue(V value) {
        if (!this.actual.containsValue(value)) throw getException();
        return (SELF) this;
    }

    public SELF containsAllKeys(Map<K, ?> expected) {
        if (!this.actual.keySet().containsAll(expected.keySet())) throw getException();
        return (SELF) this;
    }

    public SELF containsAllValues(Map<?, V> expected) {
        if (!this.actual.values().containsAll(expected.values())) throw getException();
        return (SELF) this;
    }

}

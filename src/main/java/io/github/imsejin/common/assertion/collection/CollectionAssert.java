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

import io.github.imsejin.common.assertion.object.ObjectAssert;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class CollectionAssert<SELF extends CollectionAssert<SELF, T>, T> extends ObjectAssert<SELF> {

    private final Collection<T> actual;

    public CollectionAssert(Collection<T> target) {
        super(target);
        this.actual = target;
    }

    public SELF isEmpty() {
        if (!this.actual.isEmpty()) throw getException();
        return (SELF) this;
    }

    public SELF hasElement() {
        if (this.actual.isEmpty()) throw getException();
        return (SELF) this;
    }

    public SELF hasSizeOf(int size) {
        if (this.actual.size() != size) throw getException();
        return (SELF) this;
    }

    public SELF isSameSize(Collection<?> c) {
        if (c == null || this.actual.size() != c.size()) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameSize(Collection<?> c) {
        if (c != null && this.actual.size() == c.size()) throw getException();
        return (SELF) this;
    }

    public SELF contains(T element) {
        if (!this.actual.contains(element)) throw getException();
        return (SELF) this;
    }

    public SELF containsAll(Collection<T> c) {
        if (c == null || !this.actual.containsAll(c)) throw getException();
        return (SELF) this;
    }

}

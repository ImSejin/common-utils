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
        if (!actual.isEmpty()) throw getException();
        return self;
    }

    public SELF hasElement() {
        if (actual.isEmpty()) throw getException();
        return self;
    }

    public SELF hasSizeOf(int size) {
        if (actual.size() != size) throw getException();
        return self;
    }

    public SELF isSameSize(Collection<?> expected) {
        if (expected == null || actual.size() != expected.size()) throw getException();
        return self;
    }

    public SELF isNotSameSize(Collection<?> expected) {
        if (expected != null && actual.size() == expected.size()) throw getException();
        return self;
    }

    public SELF contains(T element) {
        if (!actual.contains(element)) throw getException();
        return self;
    }

    public SELF containsAll(Collection<T> expected) {
        if (expected == null || !actual.containsAll(expected)) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ArrayAssert<?> asArray() {
        return Asserts.that(actual.toArray());
    }

}

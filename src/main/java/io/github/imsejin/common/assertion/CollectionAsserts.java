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

package io.github.imsejin.common.assertion;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class CollectionAsserts<SELF extends CollectionAsserts<SELF, T>, T> extends ObjectAsserts<SELF> {

    private final Collection<T> target;

    CollectionAsserts(Collection<T> target) {
        super(target);
        this.target = target;
    }

    public SELF hasElement() {
        if (this.target.isEmpty()) throw getException();
        return (SELF) this;
    }

    public SELF isSameSize(Collection<?> c) {
        if (c == null || this.target.size() != c.size()) throw getException();
        return (SELF) this;
    }

    public SELF contains(T element) {
        if (!this.target.contains(element)) throw getException();
        return (SELF) this;
    }

    public SELF containsAll(Collection<T> c) {
        if (c == null || !this.target.containsAll(c)) throw getException();
        return (SELF) this;
    }

}

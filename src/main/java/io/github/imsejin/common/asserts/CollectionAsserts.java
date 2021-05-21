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

package io.github.imsejin.common.asserts;

import java.util.Collection;
import java.util.function.Supplier;

public class CollectionAsserts<T> extends ObjectAsserts {

    private final Collection<T> target;

    CollectionAsserts(Collection<T> target) {
        super(target);
        this.target = target;
    }

    public CollectionAsserts<T> hasElement(Collection<?> c, String message) {
        isNotNull(message);
        if (c.isEmpty()) throw new IllegalArgumentException(message);

        return this;
    }

    public CollectionAsserts<T> hasElement(Collection<?> c, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (c.isEmpty()) throw supplier.get();

        return this;
    }

    public CollectionAsserts<T> isSameSize(Collection<?> c, String message) {
        isNotNull(message);
        if (c == null || this.target.size() != c.size()) throw new IllegalArgumentException(message);

        return this;
    }

    public CollectionAsserts<T> isSameSize(Collection<?> c, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (c == null || this.target.size() != c.size()) throw supplier.get();

        return this;
    }

    public CollectionAsserts<T> contains(T element, String message) {
        isNotNull(message);
        if (!this.target.contains(element)) throw new IllegalArgumentException(message);

        return this;
    }

    public CollectionAsserts<T> contains(T element, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.contains(element)) throw supplier.get();

        return this;
    }

    public CollectionAsserts<T> containsAll(Collection<T> c, String message) {
        isNotNull(message);
        if (c == null || !this.target.containsAll(c)) throw new IllegalArgumentException(message);

        return this;
    }

    public CollectionAsserts<T> containsAll(Collection<T> c, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (c == null || !this.target.containsAll(c)) throw supplier.get();

        return this;
    }

}

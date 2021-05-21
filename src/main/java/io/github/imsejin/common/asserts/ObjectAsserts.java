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

import java.util.function.Supplier;

public class ObjectAsserts {

    private final Object target;

    ObjectAsserts(Object target) {
        this.target = target;
    }

    public ObjectAsserts isNull(String message) {
        if (this.target != null) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isNull(Supplier<? extends RuntimeException> supplier) {
        if (this.target != null) throw supplier.get();

        return this;
    }

    public ObjectAsserts isNotNull(String message) {
        if (this.target == null) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isNotNull(Supplier<? extends RuntimeException> supplier) {
        if (this.target == null) throw supplier.get();

        return this;
    }

    public ObjectAsserts isSame(Object other, String message) {
        if (this.target != other) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isSame(Object other, Supplier<? extends RuntimeException> supplier) {
        if (this.target != other) throw supplier.get();

        return this;
    }

    public ObjectAsserts isNotSame(Object other, String message) {
        if (this.target == other) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isNotSame(Object other, Supplier<? extends RuntimeException> supplier) {
        if (this.target == other) throw supplier.get();

        return this;
    }

    public ObjectAsserts isEqualTo(Object other, String message) {
        if (!this.target.equals(other)) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isEqualTo(Object other, Supplier<? extends RuntimeException> supplier) {
        if (!this.target.equals(other)) throw supplier.get();

        return this;
    }

    public ObjectAsserts isNotEqualTo(Object other, String message) {
        if (this.target.equals(other)) throw new IllegalArgumentException(message);

        return this;
    }

    public ObjectAsserts isNotEqualTo(Object other, Supplier<? extends RuntimeException> supplier) {
        if (this.target.equals(other)) throw supplier.get();

        return this;
    }

}

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

import java.lang.reflect.Modifier;
import java.util.function.Supplier;

public class ClassAsserts<T> extends ObjectAsserts {

    private final Class<T> target;

    ClassAsserts(Class<T> target) {
        super(target);
        this.target = target;
    }

    public ClassAsserts<T> isInstanceOf(Object instance, String message) {
        isNotNull(message);
        if (!this.target.isInstance(instance)) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isInstanceOf(Object instance, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isInstance(instance)) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isAssignableFrom(Class<?> subType, String message) {
        isNotNull(message);
        if (!this.target.isAssignableFrom(subType)) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isAssignableFrom(Class<?> subType, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isAssignableFrom(subType)) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isPrimitive(String message) {
        isNotNull(message);
        if (!this.target.isPrimitive()) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isPrimitive(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isPrimitive()) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isInterface(String message) {
        isNotNull(message);
        if (!this.target.isInterface()) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isInterface(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isInterface()) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isAbstractClass(String message) {
        isNotNull(message);
        if (!Modifier.isAbstract(this.target.getModifiers())) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isAbstractClass(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!Modifier.isAbstract(this.target.getModifiers())) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isAnonymousClass(String message) {
        isNotNull(message);
        if (!this.target.isAnonymousClass()) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isAnonymousClass(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isAnonymousClass()) throw supplier.get();

        return this;
    }

    public ClassAsserts<T> isAnnotation(String message) {
        isNotNull(message);
        if (!this.target.isAnnotation()) throw new IllegalArgumentException(message);

        return this;
    }

    public ClassAsserts<T> isAnnotation(Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!this.target.isAnnotation()) throw supplier.get();

        return this;
    }

}

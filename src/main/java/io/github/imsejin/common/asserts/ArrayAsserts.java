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


import java.util.Arrays;
import java.util.function.Supplier;

public class ArrayAsserts<T> extends ObjectAsserts {

    private final T[] target;

    ArrayAsserts(T[] target) {
        super(target);
        this.target = target;
    }

    public ArrayAsserts hasElement(T[] arr, String message) {
        isNotNull(message);
        if (arr.length == 0) throw new IllegalArgumentException(message);

        return this;
    }

    public ArrayAsserts hasElement(T[] arr, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (arr.length == 0) throw supplier.get();

        return this;
    }

    public ArrayAsserts isSameSize(T[] arr, String message) {
        isNotNull(message);
        if (arr == null || this.target.length != arr.length) throw new IllegalArgumentException(message);

        return this;
    }

    public ArrayAsserts isSameSize(T[] arr, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (arr == null || this.target.length != arr.length) throw supplier.get();

        return this;
    }

    public ArrayAsserts contains(T element, String message) {
        isNotNull(message);
        if (!Arrays.asList(this.target).contains(element)) throw new IllegalArgumentException(message);

        return this;
    }

    public ArrayAsserts contains(T element, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (!Arrays.asList(this.target).contains(element)) throw supplier.get();

        return this;
    }

    public ArrayAsserts containsAll(T[] arr, String message) {
        isNotNull(message);
        if (arr == null || !Arrays.asList(this.target).containsAll(Arrays.asList(arr))) {
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    public ArrayAsserts containsAll(T[] arr, Supplier<? extends RuntimeException> supplier) {
        isNotNull(supplier);
        if (arr == null || !Arrays.asList(this.target).containsAll(Arrays.asList(arr))) throw supplier.get();

        return this;
    }

}

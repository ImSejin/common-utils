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

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayAsserts<SELF extends ArrayAsserts<SELF, T>, T> extends ObjectAsserts<SELF> {

    private final T[] target;

    ArrayAsserts(T[] target) {
        super(target);
        this.target = target;
    }

    public SELF isEmpty() {
        if (this.target.length > 0) throw getException();
        return (SELF) this;
    }

    public SELF hasElement() {
        if (this.target.length == 0) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(int length) {
        if (this.target.length != length) throw getException();
        return (SELF) this;
    }

    public SELF isSameLength(T[] arr) {
        if (arr == null || this.target.length != arr.length) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameLength(T[] arr) {
        if (arr != null && this.target.length == arr.length) throw getException();
        return (SELF) this;
    }

    public SELF contains(T element) {
        if (!Arrays.asList(this.target).contains(element)) throw getException();
        return (SELF) this;
    }

    public SELF containsAll(T[] arr) {
        if (arr == null || !Arrays.asList(this.target).containsAll(Arrays.asList(arr))) {
            throw getException();
        }

        return (SELF) this;
    }

}

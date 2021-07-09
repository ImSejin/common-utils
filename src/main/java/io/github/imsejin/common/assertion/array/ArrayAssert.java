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

package io.github.imsejin.common.assertion.array;

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

import java.util.Arrays;
import java.util.Objects;

public class ArrayAssert<SELF extends ArrayAssert<SELF>> extends AbstractObjectAssert<SELF, Object[]> {

    public ArrayAssert(Object[] actual) {
        super(actual);
    }

    @Override
    public SELF isEqualTo(Object[] expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    Arrays.toString(expected), Arrays.toString(actual));
            throw getException();
        }

        return self;
    }

    @Override
    public SELF isNotEqualTo(Object[] expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    Arrays.toString(expected), Arrays.toString(actual));
            throw getException();
        }

        return self;
    }

    public SELF isEmpty() {
        if (actual.length > 0) throw getException();
        return self;
    }

    public SELF hasElement() {
        if (actual.length == 0) throw getException();
        return self;
    }

    public SELF hasLengthOf(int expected) {
        if (actual.length != expected) throw getException();
        return self;
    }

    public SELF isSameLength(Object[] expected) {
        if (expected == null || actual.length != expected.length) throw getException();
        return self;
    }

    public SELF isNotSameLength(Object[] expected) {
        if (expected != null && actual.length == expected.length) throw getException();
        return self;
    }

    public SELF contains(Object... expected) {
        return containsAll(expected);
    }

    public SELF containsAll(Object[] expected) {
        if (expected == null || !Arrays.asList(actual).containsAll(Arrays.asList(expected))) {
            throw getException();
        }

        return self;
    }

}

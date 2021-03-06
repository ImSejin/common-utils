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

package io.github.imsejin.common.assertion.chars;

import io.github.imsejin.common.assertion.object.AbstractObjectAssert;

public abstract class AbstractCharSequenceAssert<
        SELF extends AbstractCharSequenceAssert<SELF, ACTUAL>,
        ACTUAL extends CharSequence>
        extends AbstractObjectAssert<SELF, ACTUAL> {

    protected AbstractCharSequenceAssert(ACTUAL actual) {
        super(actual);
    }

    public SELF isEmpty() {
        if (actual.length() > 0) {
            setDefaultDescription("It is expected to be empty, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotEmpty() {
        if (actual.length() <= 0) {
            setDefaultDescription("It is expected to be not empty, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF hasLengthOf(int expected) {
        if (actual.length() != expected) {
            setDefaultDescription("It is expected to have the same length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual.length());
            throw getException();
        }

        return self;
    }

    public SELF isSameLength(ACTUAL expected) {
        if (expected == null || actual.length() != expected.length()) {
            setDefaultDescription("It is expected to have the same length, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length(), actual.length());
            throw getException();
        }

        return self;
    }

    public SELF isNotSameLength(ACTUAL expected) {
        if (expected == null || actual.length() == expected.length()) {
            setDefaultDescription("It is expected not to have the same length, but it is. (expected: '{0}', actual: '{1}')",
                    expected == null ? "null" : expected.length(), actual.length());
            throw getException();
        }

        return self;
    }

}

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

import io.github.imsejin.common.assertion.object.ObjectAssert;

@SuppressWarnings("unchecked")
public class CharSequenceAssert<SELF extends CharSequenceAssert<SELF>> extends ObjectAssert<SELF> {

    private final CharSequence actual;

    public CharSequenceAssert(CharSequence actual) {
        super(actual);
        this.actual = actual;
    }

    public SELF isEmpty() {
        if (this.actual.length() > 0) throw getException();
        return (SELF) this;
    }

    public SELF isNotEmpty() {
        if (this.actual.length() <= 0) throw getException();
        return (SELF) this;
    }

    public SELF hasLengthOf(int expected) {
        if (this.actual.length() != expected) throw getException();
        return (SELF) this;
    }

    public SELF isSameLength(CharSequence expected) {
        if (expected == null || this.actual.length() != expected.length()) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameLength(CharSequence expected) {
        if (expected != null && this.actual.length() == expected.length()) throw getException();
        return (SELF) this;
    }

}

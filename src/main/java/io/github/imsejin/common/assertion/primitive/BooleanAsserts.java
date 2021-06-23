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

package io.github.imsejin.common.assertion.primitive;

import io.github.imsejin.common.assertion.Descriptor;

@SuppressWarnings("unchecked")
public class BooleanAsserts<SELF extends BooleanAsserts<SELF>> extends Descriptor<SELF> {

    private final Boolean actual;

    public BooleanAsserts(Boolean actual) {
        this.actual = actual;
    }

    public SELF isNull() {
        if (this.actual != null) {
            as("It is expected to be null, but not null. (actual: '{0}')", this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isNotNull() {
        if (this.actual == null) {
            as("It is expected to be not null, but null. (actual: 'null')");
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isTrue() {
        if (!this.actual) throw getException();
        return (SELF) this;
    }

    public SELF isFalse() {
        if (this.actual) throw getException();
        return (SELF) this;
    }

}

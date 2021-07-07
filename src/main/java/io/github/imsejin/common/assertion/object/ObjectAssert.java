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

package io.github.imsejin.common.assertion.object;

import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.reflect.ClassAssert;
import io.github.imsejin.common.tool.TypeClassifier;

@SuppressWarnings("unchecked")
public class ObjectAssert<SELF extends ObjectAssert<SELF>> extends Descriptor<SELF> {

    private final Object actual;

    public ObjectAssert(Object actual) {
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

    public SELF isSameAs(Object expected) {
        if (this.actual != expected) {
            as("They are expected to be the same, but they aren't. (expected: '{0}' actual: '{1}')", expected, this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isNotSameAs(Object expected) {
        if (this.actual == expected) {
            as("They are expected to be not the same, but they aren't. (expected: '{0}' actual: '{1}')", expected, this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isEqualTo(Object expected) {
        if (!this.actual.equals(expected)) {
            as("They are expected to be equal, but they aren't. (expected: '{0}' actual: '{1}')", expected, this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    public SELF isNotEqualTo(Object expected) {
        if (this.actual.equals(expected)) {
            as("They are expected to be not equal, but they aren't. (expected: '{0}' actual: '{1}')", expected, this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    /**
     * Verifies this is instance of the type.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param type type
     * @return whether this is instance of the type
     */
    public SELF isInstanceOf(Class<?> type) {
        if (!TypeClassifier.toWrapper(type).isInstance(this.actual)) {
            as("It is expected to be instance of the type, but it isn't. (expected: '{0}' actual: '{1}')", type, this.actual);
            throw getException();
        }

        return (SELF) this;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ClassAssert<?, ?> asClass() {
        return new ClassAssert<>(this.actual.getClass());
    }

}

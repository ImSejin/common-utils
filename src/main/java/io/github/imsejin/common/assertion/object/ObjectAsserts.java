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
import io.github.imsejin.common.assertion.reflect.ClassAsserts;
import io.github.imsejin.common.tool.TypeClassifier;

@SuppressWarnings("unchecked")
public class ObjectAsserts<SELF extends ObjectAsserts<SELF>> extends Descriptor<SELF> {

    private final Object actual;

    public ObjectAsserts(Object actual) {
        this.actual = actual;
    }

    public SELF isNull() {
        if (this.actual != null) {
            as("");
            throw getException();
        }
        return (SELF) this;
    }

    public SELF isNotNull() {
        if (this.actual == null) throw getException();
        return (SELF) this;
    }

    public SELF isSameAs(Object other) {
        if (this.actual != other) throw getException();
        return (SELF) this;
    }

    public SELF isNotSameAs(Object other) {
        if (this.actual == other) throw getException();
        return (SELF) this;
    }

    public SELF isEqualTo(Object other) {
        if (!this.actual.equals(other)) throw getException();
        return (SELF) this;
    }

    public SELF isNotEqualTo(Object other) {
        if (this.actual.equals(other)) throw getException();
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
        if (!TypeClassifier.toWrapper(type).isInstance(this.actual)) throw getException();
        return (SELF) this;
    }

    public ClassAsserts<?, ?> asClass() {
        return new ClassAsserts<>(this.actual.getClass());
    }

}

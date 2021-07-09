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

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.chars.StringAssert;
import io.github.imsejin.common.assertion.reflect.ClassAssert;
import io.github.imsejin.common.tool.TypeClassifier;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractObjectAssert<SELF extends AbstractObjectAssert<SELF, ACTUAL>, ACTUAL> extends Descriptor<SELF> {

    protected final ACTUAL actual;

    protected AbstractObjectAssert(ACTUAL actual) {
        this.actual = actual;
    }

    public SELF isNull() {
        if (actual != null) {
            setDefaultDescription("It is expected to be null, but not null. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotNull() {
        if (actual == null) {
            setDefaultDescription("It is expected to be not null, but null. (actual: 'null')");
            throw getException();
        }

        return self;
    }

    public SELF isSameAs(ACTUAL expected) {
        if (actual != expected) {
            setDefaultDescription("They are expected to be the same, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotSameAs(ACTUAL expected) {
        if (actual == expected) {
            setDefaultDescription("They are expected to be not the same, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isEqualTo(ACTUAL expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotEqualTo(ACTUAL expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
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
        if (!TypeClassifier.toWrapper(type).isInstance(actual)) {
            setDefaultDescription("It is expected to be instance of the type, but it isn't. (expected: '{0}', actual: '{1}')",
                    type, actual);
            throw getException();
        }

        return self;
    }

    public SELF predicate(Predicate<ACTUAL> predicate) {
        boolean expected = Objects.requireNonNull(predicate.test(this.actual), "Predicate is not allowed to be null");

        if (!expected) {
            setDefaultDescription("It is expected to be true, but it isn't. (expected: 'false')");
            throw getException();
        }

        return self;
    }

    public <T> SELF returns(T expected, Function<ACTUAL, T> from) {
        T actual = Objects.requireNonNull(from.apply(this.actual), "Function is not allowed to be null");

        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public ClassAssert<?, ACTUAL> asClass() {
        return (ClassAssert<?, ACTUAL>) Asserts.that(actual.getClass());
    }

    public StringAssert<?> asString() {
        return Asserts.that(actual.toString());
    }

}

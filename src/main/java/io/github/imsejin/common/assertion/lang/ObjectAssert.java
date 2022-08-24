/*
 * Copyright 2022 Sejin Im
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

package io.github.imsejin.common.assertion.lang;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.util.ClassUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectAssert<SELF extends ObjectAssert<SELF, ACTUAL>, ACTUAL> extends Descriptor<SELF> {

    /**
     * Actual value or something to be validated.
     *
     * <p> We shouldn't check if it is null in any assertion classes. The user is responsible for checking that.
     * If you want to avoid {@link NullPointerException}, you check if it is null explicitly using {@link #isNotNull()}.
     */
    protected final ACTUAL actual;

    /**
     * Creates an instance.
     *
     * <p> We recommend that invoking through {@link Asserts} than invoking through this.
     * If you follow the recommendation, you don't need to know and import the specific assertion classes.
     *
     * @param actual actual value or something to be validated
     */
    public ObjectAssert(ACTUAL actual) {
        this.actual = actual;
    }

    /**
     * Creates an instance and merges all the properties of {@link Descriptor} from parameter into this instance.
     *
     * <p> This constructor should be only used on conversion method whose name is like 'asXXX' of assertion classes.
     * That is why the modifier of constructor is {@code protected}. All subclasses override this constructor
     * as protected to be used on conversion methods and to avoid use by users. <b>The key is, if you want to allow
     * other assertion classes to use on conversion methods, override this. If you don't, don't override.</b>
     *
     * @param descriptor assertion class to merge into this
     * @param actual     actual value or something to be validated
     */
    protected ObjectAssert(Descriptor<?> descriptor, ACTUAL actual) {
        super(descriptor);
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
            setDefaultDescription("They are expected to be the same, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotSameAs(ACTUAL expected) {
        if (actual == expected) {
            setDefaultDescription("They are expected to be not the same, but they are. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isEqualTo(ACTUAL expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF isNotEqualTo(ACTUAL expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')", expected, actual);
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
     * @param expected type
     * @return whether this is instance of the type
     */
    public SELF isInstanceOf(Class<?> expected) {
        if (!ClassUtils.wrap(expected).isInstance(actual)) {
            setDefaultDescription("It is expected to be instance of the type, but it isn't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    public SELF predicate(Predicate<ACTUAL> predicate) {
        if (!Objects.requireNonNull(predicate, "Predicate is not allowed to be null").test(actual)) {
            setDefaultDescription("It is expected to be true, but it isn't. (expected: 'false')");
            throw getException();
        }

        return self;
    }

    public <T> SELF returns(T expected, Function<ACTUAL, T> from) {
        T actual = Objects.requireNonNull(from.apply(this.actual), "Function is not allowed to be null");

        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    public ClassAssert<?, ACTUAL> asClass() {
        Class<?> clazz = actual.getClass();
        return new ClassAssert<>(this, clazz);
    }

    public StringAssert<?> asString() {
        String string = this.actual.toString();
        return new StringAssert<>(this, string);
    }

}

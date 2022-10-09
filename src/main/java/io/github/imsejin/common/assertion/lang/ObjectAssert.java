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

/**
 * Assertion for {@link Object}
 *
 * <p> All the assertion classes must be extend this class. There are some plans.
 *
 * <blockquote>
 * <pre>{@code
 *     public class FooAssert<
 *             SELF extends FooAssert<SELF, ACTUAL>,
 *             ACTUAL extends Foo>
 *         extends ObjectAssert<SELF, ACTUAL> {
 *         // ...
 *     }
 * }</pre>
 *
 * <p> {@code FooAssert} allows you to override methods, to add other methods
 * and to override {@code ACTUAL} type variable to subtype. This plan is fully open
 * for other users to extend class.
 * </blockquote>
 *
 * <blockquote>
 * <pre>{@code
 *     public class FooAssert<
 *             SELF extends FooAssert<SELF>
 *         extends ObjectAssert<SELF, Foo> {
 *         // ...
 *     }
 * }</pre>
 *
 * <p> {@code FooAssert} allows you to override methods and to add other methods.
 * This plan is partially open for other users to extend class. We recommend this
 * when type of {@code ACTUAL} is final class.
 * </blockquote>
 *
 * <blockquote>
 * <pre>{@code
 *     public class FooAssert<
 *             ACTUAL extends Foo>
 *         extends ObjectAssert<FooAssert<ACTUAL>, ACTUAL> {
 *         // ...
 *     }
 * }</pre>
 *
 * <p>{@code FooAssert} doesn't allow you to override anything.
 * </blockquote>
 *
 * <blockquote>
 * <pre>{@code
 *     public class FooAssert extends ObjectAssert<FooAssert, Foo> {
 *         // ...
 *     }
 * }</pre>
 *
 * <p>{@code FooAssert} doesn't allow you to override anything.
 * </blockquote>
 *
 * @param <SELF>   this class
 * @param <ACTUAL> type of actual value to be validated
 */
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

    /**
     * Asserts that actual value is null.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(null).isNull();
     *
     *     // Assertion will fail.
     *     Asserts.that("").isNull();
     * }</pre>
     *
     * @return this class
     */
    public SELF isNull() {
        if (actual != null) {
            setDefaultDescription("It is expected to be null, but not null. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not null.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that("").isNotNull();
     *
     *     // Assertion will fail.
     *     Asserts.that(null).isNotNull();
     * }</pre>
     *
     * @return this class
     */
    public SELF isNotNull() {
        if (actual == null) {
            setDefaultDescription("It is expected to be not null, but null. (actual: 'null')");
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is the same with expected value.
     *
     * <pre>{@code
     *     Object obj = new Object();
     *
     *     // Assertion will pass.
     *     Asserts.that(0).isSameAs(0);
     *     Asserts.that(obj).isSameAs(obj);
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isSameAs(1);
     *     Asserts.that(obj).isSameAs(new Object());
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    public SELF isSameAs(ACTUAL expected) {
        if (actual != expected) {
            setDefaultDescription("They are expected to be the same, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not the same with expected value.
     *
     * <pre>{@code
     *     Object obj = new Object();
     *
     *     // Assertion will pass.
     *     Asserts.that(0).isNotSameAs(1);
     *     Asserts.that(obj).isNotSameAs(new Object());
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isNotSameAs(0);
     *     Asserts.that(obj).isNotSameAs(obj);
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    public SELF isNotSameAs(ACTUAL expected) {
        if (actual == expected) {
            setDefaultDescription("They are expected to be not the same, but they are. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isEqualTo(0);
     *     Asserts.that("alpha").isEqualTo("alpha");
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isEqualTo(1);
     *     Asserts.that("alpha").isEqualTo("beta");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    public SELF isEqualTo(ACTUAL expected) {
        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not equal to expected value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isNotEqualTo(1);
     *     Asserts.that("alpha").isNotEqualTo("beta");
     *
     *     // Assertion will fail.
     *     Asserts.that(0).isNotEqualTo(0);
     *     Asserts.that("alpha").isNotEqualTo("alpha");
     * }</pre>
     *
     * @param expected expected value
     * @return this class
     */
    public SELF isNotEqualTo(ACTUAL expected) {
        if (Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be not equal, but they are. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is the instance of expected type.
     *
     * <p> If you input a type of primitive or primitive array, it is converted to wrapper type.
     * Because primitive type can't be instantiated and Java language {@code instanceof} operator
     * always returns {@code false} with it.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isInstanceOf(Integer.class);
     *     Asserts.that(3.14).isInstanceOf(double.class);
     *     Asserts.that("alpha").isInstanceOf(CharSequence.class);
     *
     *     // Assertion will fail.
     *     Asserts.that(null).isInstanceOf(Object.class);
     *     Asserts.that(3.14).isInstanceOf(BigDecimal.class);
     *     Asserts.that(new StringBuilder()).isInstanceOf(StringBuffer.class);
     * }</pre>
     *
     * @param expected expected type
     * @return this class
     */
    public SELF isInstanceOf(Class<?> expected) {
        Class<?> wrappedType = ClassUtils.wrap(expected);
        if (!wrappedType.isInstance(actual)) {
            setDefaultDescription("It is expected to be instance of the type, but it isn't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value is not the instance of expected type.
     *
     * <p> If you input a type of primitive or primitive array, it is converted to wrapper type.
     * Because primitive type can't be instantiated and Java language {@code instanceof} operator
     * always returns {@code false} with it.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(0).isNotInstanceOf(Long.class);
     *     Asserts.that(3.14).isNotInstanceOf(BigDecimal.class);
     *     Asserts.that(new StringBuilder()).isNotInstanceOf(StringBuffer.class);
     *
     *     // Assertion will fail.
     *     Asserts.that(null).isNotInstanceOf(Object.class);
     *     Asserts.that(3.14).isNotInstanceOf(double.class);
     *     Asserts.that("alpha").isNotInstanceOf(CharSequence.class);
     * }</pre>
     *
     * @param expected expected type
     * @return this class
     */
    public SELF isNotInstanceOf(Class<?> expected) {
        Class<?> wrappedType = ClassUtils.wrap(expected);
        if (wrappedType.isInstance(actual)) {
            setDefaultDescription("It is expected not to be instance of the type, but it is. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value matches the given condition.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that('2').is(Character::isDigit);
     *     Asserts.that("alpha").is(it -> it.length() == 5);
     *
     *     // Assertion will fail.
     *     Asserts.that(Object.class).is(Class::isInterface);
     *     Asserts.that(['a', 'b', 'c']).is(List::isEmpty);
     * }</pre>
     *
     * @param condition expected condition
     * @return this class
     */
    public SELF is(Predicate<ACTUAL> condition) {
        if (!Objects.requireNonNull(condition, "Predicate cannot be null").test(actual)) {
            setDefaultDescription("It is expected to be true, but it isn't. (actual: 'false')");
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that actual value doesn't match the given condition.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(Object.class).isNot(Class::isInterface);
     *     Asserts.that(['a', 'b', 'c']).isNot(List::isEmpty);
     *
     *     // Assertion will fail.
     *     Asserts.that('2').isNot(Character::isDigit);
     *     Asserts.that("alpha").isNot(it -> it.length() == 5);
     * }</pre>
     *
     * @param condition expected condition
     * @return this class
     */
    public SELF isNot(Predicate<ACTUAL> condition) {
        if (Objects.requireNonNull(condition, "Predicate cannot be null").test(actual)) {
            setDefaultDescription("It is expected to be false, but it isn't. (actual: 'true')");
            throw getException();
        }

        return self;
    }

    /**
     * Asserts that expected value is equal to the value returned by function from actual value.
     *
     * <pre>{@code
     *     // Assertion will pass.
     *     Asserts.that(2).returns(1, it -> it / 2);
     *     Asserts.that("alpha").returns("ALPHA", String::toUpperCase);
     *
     *     // Assertion will fail.
     *     Asserts.that(Object.class).returns("Object", Class::getName);
     *     Asserts.that(['a', 'b', 'c']).returns(3, List::size);
     * }</pre>
     *
     * @param expected expected value
     * @param from     function
     * @param <T>      return type of function
     * @return this class
     */
    public <T> SELF returns(T expected, Function<ACTUAL, T> from) {
        T actual = Objects.requireNonNull(from, "Function is not allowed to be null").apply(this.actual);

        if (!Objects.deepEquals(actual, expected)) {
            setDefaultDescription("They are expected to be equal, but they aren't. (expected: '{0}', actual: '{1}')", expected, actual);
            throw getException();
        }

        return self;
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * Converts actual value into its class.
     *
     * <pre>{@code
     *     Asserts.that("alpha")
     *             .isEqualTo("alpha")
     *             .asClass()
     *             .isFinalClass();
     * }</pre>
     *
     * @return assertion for class
     */
    public ClassAssert<?, ACTUAL> asClass() {
        Class<?> clazz = actual.getClass();
        return new ClassAssert<>(this, clazz);
    }

    /**
     * Converts actual value into string.
     *
     * <pre>{@code
     *     Asserts.that(Object.class)
     *             .isEqualTo(Object.class)
     *             .asString()
     *             .endsWith("java.lang.Object");
     * }</pre>
     *
     * @return assertion for string
     */
    public StringAssert<?> asString() {
        String string = this.actual.toString();
        return new StringAssert<>(this, string);
    }

}

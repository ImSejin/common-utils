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

package io.github.imsejin.common.assertion.reflect;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.assertion.Descriptor;
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.util.ClassUtils;

public class ClassAssert<SELF extends ClassAssert<SELF, T>, T> extends AbstractObjectAssert<SELF, Class<?>> {

    public ClassAssert(Class<T> actual) {
        super(actual);
    }

    /**
     * Verifies this is type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of this type
     * @return whether this is type of the instance
     */
    public SELF isTypeOf(Object expected) {
        if (!ClassUtils.wrap(actual).isInstance(expected)) {
            setDefaultDescription("It is expected to be type of the instance, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is not type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of non-matched this type
     * @return whether this is not type of the instance
     */
    public SELF isNotTypeOf(Object expected) {
        if (ClassUtils.wrap(actual).isInstance(expected)) {
            setDefaultDescription("It is expected not to be type of the instance, but it is. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is assignable from the type.
     *
     * @param expected sub type
     * @return whether this is assignable from given type
     */
    public SELF isAssignableFrom(Class<?> expected) {
        if (!actual.isAssignableFrom(expected)) {
            setDefaultDescription("It is expected to be assignable from the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is superclass of the type.
     *
     * @param expected sub type
     * @return whether this is super class of given type
     */
    public SELF isSuperclassOf(Class<?> expected) {
        if (actual != expected.getSuperclass()) {
            setDefaultDescription("It is expected to be superclass of the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies is subclass of the type.
     *
     * @param expected super type
     * @return whether this is sub class of given type
     */
    public SELF isSubclassOf(Class<?> expected) {
        if (actual.getSuperclass() != expected) {
            setDefaultDescription("It is expected to be subclass of the given type, but it isn't. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }
        return self;
    }

    /**
     * Verifies this is primitive.
     *
     * @return whether this is primitive
     */
    public SELF isPrimitive() {
        if (!actual.isPrimitive()) {
            setDefaultDescription("It is expected to be primitive, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies whether this class is interface.
     * <p>
     * All annotations are passed this assertion
     * because they extends {@link java.lang.annotation.Annotation}.
     *
     * @return whether is is interface
     * @see #isAnnotation()
     */
    public SELF isInterface() {
        if (!actual.isInterface()) {
            setDefaultDescription("It is expected to be interface, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is annotation type.
     *
     * @return whether this is annotation type
     */
    public SELF isAnnotation() {
        if (!actual.isAnnotation()) {
            setDefaultDescription("It is expected to be annotation, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is abstract class.
     *
     * @return whether this is abstract class.
     */
    public SELF isAbstractClass() {
        if (!ClassUtils.isAbstractClass(actual)) {
            setDefaultDescription("It is expected to be abstract class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is anonymous class.
     *
     * @return whether this is anonymous class.
     */
    public SELF isAnonymousClass() {
        if (!actual.isAnonymousClass()) {
            setDefaultDescription("It is expected to be anonymous class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is type of enum.
     *
     * @return whether this is type of enum.
     */
    public SELF isEnum() {
        if (!ClassUtils.isEnumOrEnumConstant(actual)) {
            setDefaultDescription("It is expected to be enum, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is type of array.
     *
     * @return whether this is type of array.
     */
    public SELF isArray() {
        if (!actual.isArray()) {
            setDefaultDescription("It is expected to be array, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is inner class.
     *
     * @return whether this is inner class.
     */
    public SELF isMemberClass() {
        if (!actual.isMemberClass()) {
            setDefaultDescription("It is expected to be member class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    /**
     * Verifies this is local class.
     *
     * @return whether this is local class.
     */
    public SELF isLocalClass() {
        if (!actual.isLocalClass()) {
            setDefaultDescription("It is expected to be local class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ClassAssert<?, ?> asSuperclass() {
        ClassAssert<?, ?> assertion = Asserts.that(actual.getSuperclass());
        Descriptor.merge(this, assertion);

        return assertion;
    }

    public PackageAssert<?> asPackage() {
        PackageAssert<?> assertion = Asserts.that(actual.getPackage());
        Descriptor.merge(this, assertion);

        return assertion;
    }

}

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
import io.github.imsejin.common.assertion.object.AbstractObjectAssert;
import io.github.imsejin.common.tool.TypeClassifier;

import java.lang.reflect.Modifier;

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
        if (!TypeClassifier.toWrapper(actual).isInstance(expected)) {
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
        if (TypeClassifier.toWrapper(actual).isInstance(expected)) {
            setDefaultDescription("It is expected to be not type of the instance, but it is. (expected: '{0}', actual: '{1}')",
                    expected, actual);
            throw getException();
        }

        return self;
    }

    /**
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

    public SELF isAnnotation() {
        if (!actual.isAnnotation()) {
            setDefaultDescription("It is expected to be annotation, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isAbstractClass() {
        if (!Modifier.isAbstract(actual.getModifiers())) throw getException();
        return self;
    }

    public SELF isAnonymousClass() {
        if (!actual.isAnonymousClass()) {
            setDefaultDescription("It is expected to be anonymous class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isEnum() {
        if (!actual.isEnum()) throw getException();
        return self;
    }

    public SELF isArray() {
        if (!actual.isArray()) {
            setDefaultDescription("It is expected to be array, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isMemberClass() {
        if (!actual.isMemberClass()) {
            setDefaultDescription("It is expected to be member class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    public SELF isLocalClass() {
        if (!actual.isLocalClass()) {
            setDefaultDescription("It is expected to be local class, but it isn't. (actual: '{0}')", actual);
            throw getException();
        }

        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public ClassAssert<?, ?> asSuperclass() {
        return Asserts.that(actual.getSuperclass());
    }

    public PackageAssert<?> asPackage() {
        return Asserts.that(actual.getPackage());
    }

}

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

public class ClassAssert<SELF extends ClassAssert<SELF, T>, T>
        extends AbstractObjectAssert<SELF, Class<T>> {

    public ClassAssert(Class<T> actual) {
        super(actual);
    }

    /**
     * Verifies this is actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of this type
     * @return whether this is actual type of the instance
     */
    public SELF isActualTypeOf(Object expected) {
        if (!TypeClassifier.toWrapper(actual).isInstance(expected)) throw getException();
        return self;
    }

    /**
     * Verifies this is not actual type of the instance.
     * <p>
     * If you input a primitive type, it is converted to wrapper type.
     * Primitive type cannot instantiate, so return value of
     * {@link Class#isInstance(Object)} is always {@code false}.
     *
     * @param expected instance of non-matched this type
     * @return whether this is not actual type of the instance
     */
    public SELF isNotActualTypeOf(Object expected) {
        if (TypeClassifier.toWrapper(actual).isInstance(expected)) throw getException();
        return self;
    }

    /**
     * @param expected sub type
     * @return whether this is assignable from given type
     */
    public SELF isAssignableFrom(Class<?> expected) {
        if (!actual.isAssignableFrom(expected)) throw getException();
        return self;
    }

    /**
     * @param expected sub type
     * @return whether this is super class of given type
     */
    public SELF isSuperclassOf(Class<?> expected) {
        if (actual != expected.getSuperclass()) throw getException();
        return self;
    }

    /**
     * @param expected super type
     * @return whether this is sub class of given type
     */
    public SELF isSubclassOf(Class<?> expected) {
        if (actual.getSuperclass() != expected) throw getException();
        return self;
    }

    public SELF isPrimitive() {
        if (!actual.isPrimitive()) throw getException();
        return self;
    }

    public SELF isInterface() {
        if (!actual.isInterface()) throw getException();
        return self;
    }

    public SELF isAnnotation() {
        if (!actual.isAnnotation()) throw getException();
        return self;
    }

    public SELF isAbstractClass() {
        if (!Modifier.isAbstract(actual.getModifiers())) throw getException();
        return self;
    }

    public SELF isAnonymousClass() {
        if (!actual.isAnonymousClass()) throw getException();
        return self;
    }

    public SELF isEnum() {
        if (!actual.isEnum()) throw getException();
        return self;
    }

    public SELF isArray() {
        if (!actual.isArray()) throw getException();
        return self;
    }

    public SELF isMemberClass() {
        if (!actual.isMemberClass()) throw getException();
        return self;
    }

    public SELF isLocalClass() {
        if (!actual.isLocalClass()) throw getException();
        return self;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public PackageAssert<?> asPackage() {
        return Asserts.that(actual.getPackage());
    }

}
